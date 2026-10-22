package com.example.appmcmovilcare_castillo_farias.validation

import android.util.Patterns

data class SolicitudErrores(
    val equipo: String? = null,
    val servicio: String? = null,
    val descripcion: String? = null,
    val correo: String? = null
) {
    val hayErrores: Boolean
        get() = equipo != null ||
                servicio != null ||
                descripcion != null ||
                correo != null
}

object SolicitudValidator {

    val servicios = listOf(
        "Diagnóstico de falla",
        "Mantenimiento preventivo",
        "Consulta de garantía"
    )

    fun validar(
        equipoId: String,
        equiposDisponibles: Set<String>,
        servicio: String,
        descripcion: String,
        correo: String
    ): SolicitudErrores {
        return SolicitudErrores(
            equipo = when {
                equipoId.isBlank() ->
                    "Selecciona un equipo"

                equipoId !in equiposDisponibles ->
                    "El equipo seleccionado no está disponible"

                else -> null
            },
            servicio = if (servicio !in servicios) {
                "Selecciona un tipo de servicio"
            } else {
                null
            },
            descripcion = when {
                descripcion.isBlank() ->
                    "Describe el problema de tu equipo"

                descripcion.trim().length < 15 ->
                    "Escribe al menos 15 caracteres"

                descripcion.trim().length > 1000 ->
                    "Usa un máximo de 1000 caracteres"

                else -> null
            },
            correo = when {
                correo.isBlank() ->
                    "Ingresa un correo de contacto"

                !Patterns.EMAIL_ADDRESS
                    .matcher(correo.trim())
                    .matches() ->
                    "Ingresa un correo válido"

                else -> null
            }
        )
    }
}