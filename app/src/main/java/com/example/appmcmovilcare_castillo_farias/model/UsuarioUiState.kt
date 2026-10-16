package com.example.appmcmovilcare_castillo_farias.model

// Representa todos los datos que ingresa el usuario.
data class UsuarioUiState(
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val direccion: String = "",
    val aceptaTerminos: Boolean = false
)