package com.example.appmcmovilcare_castillo_farias.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmcmovilcare_castillo_farias.data.preferences.PreferenciasRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PreferenciasUiState(
    val cargando: Boolean = true,
    val guardando: Boolean = false,
    val mostrarDiagnosticos: Boolean = true,
    val mostrarConfirmacion: Boolean = false,
    val error: String? = null
)

class PreferenciasViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = PreferenciasRepository(application)

    private val _estado = MutableStateFlow(PreferenciasUiState())
    val estado = _estado.asStateFlow()

    private var cargaJob: Job? = null
    private var mensajeJob: Job? = null

    init {
        cargarPreferencias()
    }

    fun cargarPreferencias() {
        cargaJob?.cancel()

        cargaJob = viewModelScope.launch {
            _estado.update {
                it.copy(cargando = true, error = null)
            }

            try {
                repository.mostrarDiagnosticos.collect { valor ->
                    _estado.update {
                        it.copy(
                            cargando = false,
                            mostrarDiagnosticos = valor
                        )
                    }
                }
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                _estado.update {
                    it.copy(
                        cargando = false,
                        error = "No se pudieron leer las preferencias."
                    )
                }
            }
        }
    }

    fun cambiarMostrarDiagnosticos(valor: Boolean) {
        val actual = _estado.value

        if (
            actual.cargando ||
            actual.guardando ||
            actual.error != null
        ) {
            return
        }

        mensajeJob?.cancel()

        _estado.update {
            it.copy(
                guardando = true,
                mostrarConfirmacion = false
            )
        }

        viewModelScope.launch {
            try {
                repository.guardarMostrarDiagnosticos(valor)

                _estado.update {
                    it.copy(
                        guardando = false,
                        mostrarDiagnosticos = valor,
                        mostrarConfirmacion = true
                    )
                }

                mensajeJob = viewModelScope.launch {
                    delay(2500)

                    _estado.update {
                        it.copy(mostrarConfirmacion = false)
                    }
                }
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                _estado.update {
                    it.copy(
                        guardando = false,
                        error = "No se pudo guardar la preferencia."
                    )
                }
            }
        }
    }
}