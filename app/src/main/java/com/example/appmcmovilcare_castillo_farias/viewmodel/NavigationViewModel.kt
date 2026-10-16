package com.example.appmcmovilcare_castillo_farias.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmcmovilcare_castillo_farias.navigation.AppRoutes
import com.example.appmcmovilcare_castillo_farias.navigation.NavigationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class NavigationViewModel : ViewModel() {

    // Guarda los eventos hasta que la interfaz los reciba.
    private val navigationChannel =
        Channel<NavigationEvent>(Channel.BUFFERED)

    // La interfaz observará este flujo para ejecutar la navegación.
    val navigationEvents = navigationChannel.receiveAsFlow()

    fun navegarA(destination: AppRoutes) {
        viewModelScope.launch {
            navigationChannel.send(
                NavigationEvent.NavigateTo(destination)
            )
        }
    }

    fun volver() {
        viewModelScope.launch {
            navigationChannel.send(
                NavigationEvent.NavigateBack
            )
        }
    }
}