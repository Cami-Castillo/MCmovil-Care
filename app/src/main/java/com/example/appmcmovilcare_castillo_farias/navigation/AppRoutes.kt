package com.example.appmcmovilcare_castillo_farias.navigation

// Destinos disponibles en la aplicación.
sealed class AppRoutes(val route: String) {
    object Inicio : AppRoutes("inicio")
    object MisEquipos : AppRoutes("mis_equipos")
    object SolicitarAtencion : AppRoutes("solicitar_atencion")

    // Pantallas del formulario de usuario.
    object Registro : AppRoutes("registro")
    object Resumen : AppRoutes("resumen")
}