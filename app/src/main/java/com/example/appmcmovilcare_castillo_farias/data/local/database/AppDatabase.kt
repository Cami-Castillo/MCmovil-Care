package com.example.appmcmovilcare_castillo_farias.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.appmcmovilcare_castillo_farias.data.local.dao.EquipoDao
import com.example.appmcmovilcare_castillo_farias.data.local.dao.SolicitudDao
import com.example.appmcmovilcare_castillo_farias.data.local.entity.EquipoEntity
import com.example.appmcmovilcare_castillo_farias.data.local.entity.SolicitudEntity

@Database(
    entities = [
        EquipoEntity::class,
        SolicitudEntity::class
    ],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun equipoDao(): EquipoDao

    abstract fun solicitudDao(): SolicitudDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {

            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS solicitudes (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        equipoId TEXT NOT NULL,
                        tipoServicio TEXT NOT NULL,
                        descripcion TEXT NOT NULL,
                        correoContacto TEXT NOT NULL,
                        fechaCreacion INTEGER NOT NULL,
                        estado TEXT NOT NULL,
                        imagenRuta TEXT
                    )
                    """.trimIndent()
                )
            }
        }

        fun obtenerInstancia(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mcmovil_care.db"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }
}