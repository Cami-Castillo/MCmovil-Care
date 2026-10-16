package com.example.appmcmovilcare_castillo_farias.model

// Guarda el mensaje de validación de cada campo.
// null significa que el campo no tiene un error.
data class UsuarioErrores(
    val nombre: String? = null,
    val correo: String? = null,
    val clave: String? = null,
    val direccion: String? = null,
    val aceptaTerminos: String? = null
)