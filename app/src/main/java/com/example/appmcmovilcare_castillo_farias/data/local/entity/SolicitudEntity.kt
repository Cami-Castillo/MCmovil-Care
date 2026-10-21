package com.example.appmcmovilcare_castillo_farias.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "solicitudes")
data class SolicitudEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val equipoId: String,
    val tipoServicio: String,
    val descripcion: String,
    val correoContacto: String,
    val fechaCreacion: Long,
    val estado: String = "Recibida",
    val imagenRuta: String? = null
)