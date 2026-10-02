package com.ecohabits.data.repository

import android.util.Log
import com.ecohabits.data.remote.dto.BadgeDto
import com.ecohabits.data.remote.dto.UserBadgeDto
import com.ecohabits.domain.model.Badge
import com.ecohabits.domain.repository.BadgeRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BadgeRepositoryImpl @Inject constructor(
    private val supabase: SupabaseClient
) : BadgeRepository {

    override suspend fun getAllBadges(): List<Badge> {
        return try {

            val badgeDtos =
                supabase
                    .postgrest["TblBadges"]
                    .select()
                    .decodeList<BadgeDto>()

            val badges =
                badgeDtos.map { dto ->
                    dto.toDomain()
                }

            Log.d(
                "BadgeRepository",
                "Insignias obtenidas: ${badges.size}"
            )

            badges

        } catch (e: Exception) {

            Log.e(
                "BadgeRepository",
                "Error obteniendo insignias: ${e.message}",
                e
            )

            throw e
        }
    }

    override suspend fun getUserBadges(
        userId: String
    ): List<Badge> {
        return try {

            val badges =
                supabase
                    .postgrest["TblBadges"]
                    .select()
                    .decodeList<BadgeDto>()

            val userBadges =
                supabase
                    .postgrest["TblBadgesUsers"]
                    .select {
                        filter {
                            eq(
                                "IdUserFK",
                                userId
                            )
                        }
                    }
                    .decodeList<UserBadgeDto>()

            Log.d(
                "BadgeRepository",
                "TblBadges=${badges.size}, " +
                        "TblBadgesUsers=${userBadges.size}"
            )

            val unlockedMap =
                userBadges.associateBy {
                    it.idBadgeFK
                }

            val result =
                badges.map { dto ->

                    val userBadge =
                        unlockedMap[
                            dto.idBadge
                        ]

                    dto.toDomain(
                        unlocked =
                            userBadge != null,

                        unlockedDate =
                            userBadge?.date
                    )
                }

            Log.d(
                "BadgeRepository",
                "Insignias procesadas=${result.size}, " +
                        "desbloqueadas=${result.count { it.unlocked }}"
            )

            result

        } catch (e: Exception) {

            Log.e(
                "BadgeRepository",
                "Error obteniendo insignias del usuario: ${e.message}",
                e
            )

            throw e
        }
    }

    override suspend fun unlockBadge(
        userId: String,
        badgeId: String
    ): Boolean {
        return try {

            val alreadyUnlocked =
                supabase
                    .postgrest["TblBadgesUsers"]
                    .select {
                        filter {

                            eq(
                                "IdUserFK",
                                userId
                            )

                            eq(
                                "IdBadgeFK",
                                badgeId
                            )
                        }
                    }
                    .decodeList<UserBadgeDto>()

            if (alreadyUnlocked.isNotEmpty()) {

                Log.d(
                    "BadgeRepository",
                    "La insignia $badgeId ya estaba desbloqueada."
                )

                return false
            }

            val dateFormat =
                SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.US
                )

            val currentDate =
                dateFormat.format(
                    Date()
                )

            val userBadge =
                UserBadgeDto(
                    idBadgeFK = badgeId,
                    idUserFK = userId,
                    date = currentDate
                )

            supabase
                .postgrest["TblBadgesUsers"]
                .insert(userBadge)

            Log.d(
                "BadgeRepository",
                "Insignia $badgeId desbloqueada para usuario $userId"
            )

            true

        } catch (e: Exception) {

            Log.e(
                "BadgeRepository",
                "Error desbloqueando insignia $badgeId: ${e.message}",
                e
            )

            throw e
        }
    }

    private fun BadgeDto.toDomain(
        unlocked: Boolean = false,
        unlockedDate: String? = null
    ): Badge {

        return Badge(
            id = idBadge,
            name = badgeName,
            description = badgeDescription,
            code = badgeCode.orEmpty(),
            ruleType = ruleType.orEmpty(),
            ruleValue = ruleValue ?: 0,
            unlocked = unlocked,
            unlockedDate = unlockedDate
        )
    }
}