package com.example.appmcmovilcare_castillo_farias.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.appmcmovilcare_castillo_farias.model.UsuarioErrores
import com.example.appmcmovilcare_castillo_farias.model.UsuarioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel : ViewModel() {

    // Estado privado: solo el ViewModel puede modificarlo.
    private val _estado = MutableStateFlow(UsuarioUiState())
    val estado = _estado.asStateFlow()

    private val _errores = MutableStateFlow(UsuarioErrores())
    val errores = _errores.asStateFlow()

    fun onNombreChange(nuevo: String) {
        _estado.update { it.copy(nombre = nuevo) }
        _errores.update { it.copy(nombre = validarNombre(nuevo)) }
    }

    fun onCorreoChange(nuevo: String) {
        _estado.update { it.copy(correo = nuevo) }
        _errores.update { it.copy(correo = validarCorreo(nuevo)) }
    }

    fun onClaveChange(nuevo: String) {
        _estado.update { it.copy(clave = nuevo) }
        _errores.update { it.copy(clave = validarClave(nuevo)) }
    }

    fun onDireccionChange(nuevo: String) {
        _estado.update { it.copy(direccion = nuevo) }
        _errores.update { it.copy(direccion = validarDireccion(nuevo)) }
    }

    fun onAceptarTerminosChange(valor: Boolean) {
        _estado.update { it.copy(aceptaTerminos = valor) }
        _errores.update {
            it.copy(aceptaTerminos = validarTerminos(valor))
        }
    }

    // Revisa todos los campos antes de permitir la navegación.
    fun validarFormulario(): Boolean {
        val datos = _estado.value

        val nuevosErrores = UsuarioErrores(
            nombre = validarNombre(datos.nombre),
            correo = validarCorreo(datos.correo),
            clave = validarClave(datos.clave),
            direccion = validarDireccion(datos.direccion),
            aceptaTerminos = validarTerminos(datos.aceptaTerminos)
        )

        _errores.value = nuevosErrores

        return nuevosErrores.nombre == null &&
                nuevosErrores.correo == null &&
                nuevosErrores.clave == null &&
                nuevosErrores.direccion == null &&
                nuevosErrores.aceptaTerminos == null
    }

    private fun validarNombre(nombre: String): String? {
        return when {
            nombre.isBlank() -> "Ingresa tu nombre"
            nombre.trim().length < 3 -> "Usa al menos 3 caracteres"
            else -> null
        }
    }

    private fun validarCorreo(correo: String): String? {
        return when {
            correo.isBlank() -> "Ingresa tu correo"
            !Patterns.EMAIL_ADDRESS.matcher(correo.trim()).matches() ->
                "Ingresa un correo válido"
            else -> null
        }
    }

    private fun validarClave(clave: String): String? {
        return when {
            clave.isBlank() -> "Ingresa una clave"
            clave.length < 8 -> "La clave debe tener al menos 8 caracteres"
            else -> null
        }
    }

    private fun validarDireccion(direccion: String): String? {
        return when {
            direccion.isBlank() -> "Ingresa tu dirección"
            direccion.trim().length < 5 -> "Usa al menos 5 caracteres"
            else -> null
        }
    }

    private fun validarTerminos(acepta: Boolean): String? {
        return if (acepta) {
            null
        } else {
            "Debes aceptar los términos"
        }
    }
}