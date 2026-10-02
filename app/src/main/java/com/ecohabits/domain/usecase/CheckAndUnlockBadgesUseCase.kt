package com.ecohabits.domain.usecase

import com.ecohabits.domain.model.Badge
import com.ecohabits.domain.model.BadgeCheckResult
import com.ecohabits.domain.repository.AuthRepository
import com.ecohabits.domain.repository.BadgeRepository
import com.ecohabits.domain.repository.ChallengeRepository
import javax.inject.Inject

class CheckAndUnlockBadgesUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val badgeRepository: BadgeRepository,
    private val challengeRepository: ChallengeRepository,
    private val getUserStreakUseCase: GetUserStreakUseCase
) {

    suspend operator fun invoke(): Result<BadgeCheckResult> {
        return try {

            val userId =
                authRepository.getCurrentUserId()
                    ?: return Result.failure(
                        Exception("Usuario no autenticado")
                    )

            val challenges =
                challengeRepository.getUserChallenges(
                    userId
                )

            val completedChallenges =
                challenges.filter {
                    it.isCompleted
                }

            val completedChallengesCount =
                completedChallenges.size

            val totalPoints =
                completedChallenges.sumOf {
                    it.points
                }

            /*
             * 2. Obtenemos la racha usando el UseCase
             * que ya construimos anteriormente.
             */
            val streak =
                getUserStreakUseCase()
                    .getOrElse {
                        0
                    }

            /*
             * 3. Obtenemos todas las insignias.
             *
             * BadgeRepository ya nos indica cuáles
             * están desbloqueadas para este usuario.
             */
            val badges =
                badgeRepository.getUserBadges(
                    userId
                )

            /*
             * 4. Solamente evaluamos las que todavía
             * NO han sido desbloqueadas.
             */
            val lockedBadges =
                badges.filter {
                    !it.unlocked
                }

            val newlyUnlockedBadges =
                mutableListOf<Badge>()

            /*
             * 5. Evaluamos las reglas configuradas
             * en Supabase.
             */
            lockedBadges.forEach { badge ->

                val requirementMet =
                    isRequirementMet(
                        badge = badge,
                        completedChallengesCount =
                            completedChallengesCount,
                        totalPoints =
                            totalPoints,
                        streak =
                            streak
                    )

                if (requirementMet) {

                    val unlockedNow =
                        badgeRepository.unlockBadge(
                            userId = userId,
                            badgeId = badge.id
                        )

                    if (unlockedNow) {

                        newlyUnlockedBadges.add(
                            badge.copy(
                                unlocked = true
                            )
                        )
                    }
                }
            }

            val updatedBadges =
                if (newlyUnlockedBadges.isNotEmpty()) {

                    badgeRepository.getUserBadges(
                        userId
                    )

                } else {

                    badges
                }

            Result.success(
                BadgeCheckResult(
                    badges = updatedBadges,
                    newlyUnlockedBadges =
                        newlyUnlockedBadges
                )
            )

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    private fun isRequirementMet(
        badge: Badge,
        completedChallengesCount: Int,
        totalPoints: Int,
        streak: Int
    ): Boolean {

        return when (
            badge.ruleType
                .trim()
                .uppercase()
        ) {

            RULE_COMPLETED_CHALLENGES -> {
                completedChallengesCount >=
                        badge.ruleValue
            }

            RULE_POINTS -> {
                totalPoints >=
                        badge.ruleValue
            }

            RULE_STREAK -> {
                streak >=
                        badge.ruleValue
            }

            else -> {
                false
            }
        }
    }

    private companion object {

        const val RULE_COMPLETED_CHALLENGES =
            "COMPLETED_CHALLENGES"

        const val RULE_POINTS =
            "POINTS"

        const val RULE_STREAK =
            "STREAK"
    }
}