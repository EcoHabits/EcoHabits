package com.ecohabits.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ecohabits.data.local.dao.ChallengeDao
import com.ecohabits.data.local.dao.UserContextDao
import com.ecohabits.data.local.entity.ChallengeEntity
import com.ecohabits.data.local.entity.UserContextEntity

@Database(entities = [ChallengeEntity::class, UserContextEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun challengeDao() : ChallengeDao
    abstract fun userContextDao(): UserContextDao
    // Se usa companion object ya que es una especie de singleton y esto nos permite garantizar que solo se cree una instancia de la base de datos
    companion object {
        @Volatile // Volatile garantiza la seguridad de los subprocesos de la instancia ademas de que permite exponer cambios a otros subprocesos
        private var INSTANCE: AppDatabase? = null
        fun getDataBase(context: Context) : AppDatabase{
            // Importante pasar el context como parametro porque mas adelante lo usaremos para obtener el contexto de la aplicacion
            // A como se menciono aqui usamos el contexto de actividad pasando como parametro this
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "App_Data_Base").build()
                INSTANCE = instance
                instance
            }
        }

    }
}