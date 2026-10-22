package com.example.appmcmovilcare_castillo_farias.model

import com.example.appmcmovilcare_castillo_farias.data.local.entity.EquipoEntity
import com.example.appmcmovilcare_castillo_farias.data.local.entity.SolicitudEntity
import com.example.appmcmovilcare_castillo_farias.validation.SolicitudErrores

data class SolicitudUiState(
    val cargando: Boolean = true,
    val guardando: Boolean = false,
    val procesandoImagen: Boolean = false,
    val equipos: List<EquipoEntity> = emptyList(),
    val solicitudes: List<SolicitudEntity> = emptyList(),
    val equipoId: String = "",
    val servicio: String = "",
    val descripcion: String = "",
    val correo: String = "",
    val imagenRuta: String? = null,
    val errores: SolicitudErrores = SolicitudErrores(),
    val intentoEnviar: Boolean = false,
    val solicitudCreadaId: Long? = null,
    val errorCarga: String? = null,
    val errorGuardado: String? = null,
    val errorImagen: String? = null
)