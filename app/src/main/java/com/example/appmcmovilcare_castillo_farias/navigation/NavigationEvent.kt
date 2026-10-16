package com.example.appmcmovilcare_castillo_farias.navigation

// Acciones de navegación que puede solicitar el ViewModel.
sealed class NavigationEvent {

    // Ir a una pantalla de la aplicación.
    data class NavigateTo(
        val destination: AppRoutes
    ) : NavigationEvent()

    // Volver a la pantalla anterior.
    object NavigateBack : NavigationEvent()
}