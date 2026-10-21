package com.example.appmcmovilcare_castillo_farias.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmcmovilcare_castillo_farias.data.local.database.AppDatabase
import com.example.appmcmovilcare_castillo_farias.data.local.entity.EquipoEntity
import com.example.appmcmovilcare_castillo_farias.data.repository.EquipoRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class EquiposUiState(
    val cargando: Boolean = true,
    val equipos: List<EquipoEntity> = emptyList(),
    val error: String? = null
)

class EquiposViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = EquipoRepository(
        AppDatabase.obtenerInstancia(application).equipoDao()
    )

    private val _estado = MutableStateFlow(EquiposUiState())
    val estado = _estado.asStateFlow()

    private var cargaJob: Job? = null

    init {
        cargarEquipos()
    }

    fun cargarEquipos() {
        cargaJob?.cancel()

        cargaJob = viewModelScope.launch {
            _estado.value = EquiposUiState(cargando = true)

            try {
                repository.cargarEquiposDePrueba()

                repository.observarEquipos().collect { equipos ->
                    _estado.value = EquiposUiState(
                        cargando = false,
                        equipos = equipos
                    )
                }
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                _estado.value = EquiposUiState(
                    cargando = false,
                    error = "No se pudieron cargar los equipos."
                )
            }
        }
    }
}