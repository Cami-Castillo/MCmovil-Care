package com.example.appmcmovilcare_castillo_farias.data.repository

import com.example.appmcmovilcare_castillo_farias.data.local.dao.SolicitudDao
import com.example.appmcmovilcare_castillo_farias.data.local.entity.SolicitudEntity
import kotlinx.coroutines.flow.Flow

class SolicitudRepository(
    private val solicitudDao: SolicitudDao
) {

    fun observarSolicitudes(): Flow<List<SolicitudEntity>> {
        return solicitudDao.observarSolicitudes()
    }

    suspend fun guardar(solicitud: SolicitudEntity): Long {
        return solicitudDao.insertar(solicitud)
    }
}