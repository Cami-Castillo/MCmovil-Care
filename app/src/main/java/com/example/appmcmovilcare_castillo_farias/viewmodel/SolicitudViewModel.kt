package com.example.appmcmovilcare_castillo_farias.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmcmovilcare_castillo_farias.data.local.database.AppDatabase
import com.example.appmcmovilcare_castillo_farias.data.local.entity.SolicitudEntity
import com.example.appmcmovilcare_castillo_farias.data.repository.EquipoRepository
import com.example.appmcmovilcare_castillo_farias.data.repository.SolicitudRepository
import com.example.appmcmovilcare_castillo_farias.model.SolicitudUiState
import com.example.appmcmovilcare_castillo_farias.validation.SolicitudValidator
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SolicitudViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database = AppDatabase.obtenerInstancia(application)

    private val equipoRepository = EquipoRepository(
        database.equipoDao()
    )

    private val solicitudRepository = SolicitudRepository(
        database.solicitudDao()
    )

    private val _estado = MutableStateFlow(SolicitudUiState())
    val estado = _estado.asStateFlow()

    private var cargaJob: Job? = null

    init {
        cargarDatos()
    }

    fun cargarDatos() {
        cargaJob?.cancel()

        cargaJob = viewModelScope.launch {
            _estado.update {
                it.copy(
                    cargando = true,
                    errorCarga = null
                )
            }

            try {
                equipoRepository.cargarEquiposDePrueba()

                combine(
                    equipoRepository.observarEquipos(),
                    solicitudRepository.observarSolicitudes()
                ) { equipos, solicitudes ->
                    equipos to solicitudes
                }.collect { (equipos, solicitudes) ->
                    _estado.update {
                        it.copy(
                            cargando = false,
                            equipos = equipos,
                            solicitudes = solicitudes,
                            errorCarga = null
                        )
                    }
                }
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                _estado.update {
                    it.copy(
                        cargando = false,
                        errorCarga = "No se pudieron cargar los datos."
                    )
                }
            }
        }
    }

    fun cambiarEquipo(valor: String) {
        actualizarFormulario { it.copy(equipoId = valor) }
    }

    fun cambiarServicio(valor: String) {
        actualizarFormulario { it.copy(servicio = valor) }
    }

    fun cambiarDescripcion(valor: String) {
        actualizarFormulario { it.copy(descripcion = valor) }
    }

    fun cambiarCorreo(valor: String) {
        actualizarFormulario { it.copy(correo = valor) }
    }

    private fun actualizarFormulario(
        cambio: (SolicitudUiState) -> SolicitudUiState
    ) {
        if (_estado.value.guardando) return

        _estado.update { actual ->
            val nuevo = cambio(actual).copy(
                solicitudCreadaId = null,
                errorGuardado = null
            )

            if (nuevo.intentoEnviar) {
                nuevo.copy(errores = validar(nuevo))
            } else {
                nuevo
            }
        }
    }

    private fun validar(datos: SolicitudUiState) =
        SolicitudValidator.validar(
            equipoId = datos.equipoId,
            equiposDisponibles = datos.equipos.map { it.id }.toSet(),
            servicio = datos.servicio,
            descripcion = datos.descripcion,
            correo = datos.correo
        )

    fun enviarSolicitud() {
        val datos = _estado.value

        if (
            datos.cargando ||
            datos.guardando ||
            datos.errorCarga != null
        ) {
            return
        }

        val errores = validar(datos)

        _estado.update {
            it.copy(
                errores = errores,
                intentoEnviar = true,
                solicitudCreadaId = null,
                errorGuardado = null
            )
        }

        if (errores.hayErrores) return

        // Se bloquean nuevos envíos antes de iniciar la corrutina.
        _estado.update { it.copy(guardando = true) }

        viewModelScope.launch {
            try {
                val solicitud = SolicitudEntity(
                    equipoId = datos.equipoId,
                    tipoServicio = datos.servicio,
                    descripcion = datos.descripcion.trim(),
                    correoContacto = datos.correo.trim(),
                    fechaCreacion = System.currentTimeMillis()
                )

                val id = solicitudRepository.guardar(solicitud)

                _estado.update {
                    it.copy(
                        guardando = false,
                        equipoId = "",
                        servicio = "",
                        descripcion = "",
                        correo = "",
                        errores = com.example
                            .appmcmovilcare_castillo_farias
                            .validation.SolicitudErrores(),
                        intentoEnviar = false,
                        solicitudCreadaId = id
                    )
                }
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                _estado.update {
                    it.copy(
                        guardando = false,
                        errorGuardado =
                            "No se pudo guardar. Tus datos siguen en el formulario."
                    )
                }
            }
        }
    }
}