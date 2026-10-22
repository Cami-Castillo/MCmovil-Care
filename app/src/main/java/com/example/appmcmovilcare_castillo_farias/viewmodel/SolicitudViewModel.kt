package com.example.appmcmovilcare_castillo_farias.viewmodel

import android.app.Application
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmcmovilcare_castillo_farias.data.local.database.AppDatabase
import com.example.appmcmovilcare_castillo_farias.data.local.entity.SolicitudEntity
import com.example.appmcmovilcare_castillo_farias.data.repository.EquipoRepository
import com.example.appmcmovilcare_castillo_farias.data.repository.SolicitudRepository
import com.example.appmcmovilcare_castillo_farias.model.SolicitudUiState
import com.example.appmcmovilcare_castillo_farias.validation.SolicitudErrores
import com.example.appmcmovilcare_castillo_farias.validation.SolicitudValidator
import java.io.File
import java.io.IOException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
                it.copy(cargando = true, errorCarga = null)
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

    fun mostrarErrorImagen(mensaje: String) {
        _estado.update { it.copy(errorImagen = mensaje) }
    }

    fun adjuntarImagen(uri: Uri) {
        val actual = _estado.value

        if (actual.guardando || actual.procesandoImagen) return

        _estado.update {
            it.copy(
                procesandoImagen = true,
                errorImagen = null,
                solicitudCreadaId = null
            )
        }

        viewModelScope.launch {
            try {
                val nombre = withContext(Dispatchers.IO) {
                    copiarImagen(uri)
                }

                _estado.update {
                    it.copy(
                        imagenRuta = nombre,
                        procesandoImagen = false
                    )
                }
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                _estado.update {
                    it.copy(
                        procesandoImagen = false,
                        errorImagen =
                            "No se pudo adjuntar. Usa una imagen válida de hasta 10 MB."
                    )
                }
            } finally {
                // Solo se elimina el archivo temporal de nuestra cámara.
                val app = getApplication<Application>()

                if (uri.authority == "${app.packageName}.fileprovider") {
                    withContext(Dispatchers.IO) {
                        runCatching {
                            app.contentResolver.delete(uri, null, null)
                        }
                    }
                }
            }
        }
    }

    private fun copiarImagen(uri: Uri): String {
        val app = getApplication<Application>()
        val carpeta = File(app.filesDir, "adjuntos")

        if (!carpeta.exists() && !carpeta.mkdirs()) {
            throw IOException("No se pudo crear la carpeta")
        }

        val destino = File.createTempFile(
            "solicitud_",
            ".img",
            carpeta
        )

        try {
            val entrada = app.contentResolver.openInputStream(uri)
                ?: throw IOException("No se pudo abrir la imagen")

            entrada.use { input ->
                destino.outputStream().use { output ->
                    val buffer = ByteArray(8192)
                    var total = 0L

                    while (true) {
                        val leidos = input.read(buffer)
                        if (leidos == -1) break

                        total += leidos

                        if (total > 10L * 1024 * 1024) {
                            throw IOException("Imagen demasiado grande")
                        }

                        output.write(buffer, 0, leidos)
                    }
                }
            }

            val opciones = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }

            BitmapFactory.decodeFile(destino.absolutePath, opciones)

            if (opciones.outWidth <= 0 || opciones.outHeight <= 0) {
                throw IOException("El archivo no es una imagen compatible")
            }

            return destino.name
        } catch (error: Exception) {
            destino.delete()
            throw error
        }
    }

    fun quitarImagen() {
        if (_estado.value.guardando || _estado.value.procesandoImagen) {
            return
        }

        _estado.update {
            it.copy(
                imagenRuta = null,
                errorImagen = null,
                solicitudCreadaId = null
            )
        }
    }

    fun enviarSolicitud() {
        val datos = _estado.value

        if (
            datos.cargando ||
            datos.guardando ||
            datos.procesandoImagen ||
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

        _estado.update { it.copy(guardando = true) }

        viewModelScope.launch {
            try {
                val solicitud = SolicitudEntity(
                    equipoId = datos.equipoId,
                    tipoServicio = datos.servicio,
                    descripcion = datos.descripcion.trim(),
                    correoContacto = datos.correo.trim(),
                    fechaCreacion = System.currentTimeMillis(),
                    imagenRuta = datos.imagenRuta
                )

                val id = solicitudRepository.guardar(solicitud)

                _estado.update {
                    it.copy(
                        guardando = false,
                        equipoId = "",
                        servicio = "",
                        descripcion = "",
                        correo = "",
                        imagenRuta = null,
                        errorImagen = null,
                        errores = SolicitudErrores(),
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