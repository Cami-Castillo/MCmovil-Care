package com.example.appmcmovilcare_castillo_farias.navigation

sealed class AppRoutes(val route: String) {

    object Inicio : AppRoutes("inicio")

    object MisEquipos : AppRoutes("mis_equipos")

    object SolicitarAtencion : AppRoutes("solicitar_atencion")

    object Registro : AppRoutes("registro")

    object Resumen : AppRoutes("resumen")

    object Preferencias : AppRoutes("preferencias")
}