package com.example.appmcmovilcare_castillo_farias.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "equipos")
data class EquipoEntity(
    @PrimaryKey
    val id: String,
    val nombre: String,
    val marca: String,
    val modelo: String,
    val numeroSerie: String,
    val estado: String,
    val diagnostico: String
)