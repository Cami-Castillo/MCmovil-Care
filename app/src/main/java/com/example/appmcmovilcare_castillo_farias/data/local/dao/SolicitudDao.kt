package com.example.appmcmovilcare_castillo_farias.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.appmcmovilcare_castillo_farias.data.local.entity.SolicitudEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SolicitudDao {

    @Insert
    suspend fun insertar(solicitud: SolicitudEntity): Long

    @Query(
        """
        SELECT * FROM solicitudes
        ORDER BY fechaCreacion DESC, id DESC
        """
    )
    fun observarSolicitudes(): Flow<List<SolicitudEntity>>
}