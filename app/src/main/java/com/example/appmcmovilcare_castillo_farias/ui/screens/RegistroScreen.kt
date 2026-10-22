package com.example.appmcmovilcare_castillo_farias.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.appmcmovilcare_castillo_farias.viewmodel.UsuarioViewModel

@Composable
fun RegistroScreen(
    usuarioViewModel: UsuarioViewModel,
    onVerResumen: () -> Unit
) {
    val estado by usuarioViewModel.estado.collectAsStateWithLifecycle()
    val errores by usuarioViewModel.errores.collectAsStateWithLifecycle()

    var claveVisible by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Registro de usuario",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Formulario de demostración. Revisa tus datos " +
                    "en el resumen; este paso no crea una cuenta."
        )

        OutlinedTextField(
            value = estado.nombre,
            onValueChange = usuarioViewModel::onNombreChange,
            label = { Text("Nombre completo") },
            leadingIcon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (errores.nombre != null) {
                    Icon(
                        Icons.Default.Error,
                        contentDescription = "Error en el nombre"
                    )
                }
            },
            singleLine = true,
            isError = errores.nombre != null,
            supportingText = {
                errores.nombre?.let { Text(it) }
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.correo,
            onValueChange = usuarioViewModel::onCorreoChange,
            label = { Text("Correo electrónico") },
            leadingIcon = {
                Icon(
                    Icons.Default.Email,
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (errores.correo != null) {
                    Icon(
                        Icons.Default.Error,
                        contentDescription = "Error en el correo"
                    )
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            isError = errores.correo != null,
            supportingText = {
                errores.correo?.let { Text(it) }
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.clave,
            onValueChange = usuarioViewModel::onClaveChange,
            label = { Text("Clave de prueba") },
            leadingIcon = {
                Icon(
                    Icons.Default.Lock,
                    contentDescription = null
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        claveVisible = !claveVisible
                    }
                ) {
                    Icon(
                        imageVector = if (claveVisible) {
                            Icons.Default.VisibilityOff
                        } else {
                            Icons.Default.Visibility
                        },
                        contentDescription = if (claveVisible) {
                            "Ocultar clave"
                        } else {
                            "Mostrar clave"
                        }
                    )
                }
            },
            singleLine = true,
            visualTransformation = if (claveVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            isError = errores.clave != null,
            supportingText = {
                Text(
                    errores.clave
                        ?: "Usa al menos 8 caracteres. No ingreses una clave real."
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.direccion,
            onValueChange = usuarioViewModel::onDireccionChange,
            label = { Text("Dirección de prueba") },
            leadingIcon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (errores.direccion != null) {
                    Icon(
                        Icons.Default.Error,
                        contentDescription = "Error en la dirección"
                    )
                }
            },
            minLines = 2,
            maxLines = 4,
            isError = errores.direccion != null,
            supportingText = {
                errores.direccion?.let { Text(it) }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(
                    value = estado.aceptaTerminos,
                    role = Role.Checkbox,
                    onValueChange =
                        usuarioViewModel::onAceptarTerminosChange
                )
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = estado.aceptaTerminos,
                onCheckedChange = null
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = "Acepto los términos del registro de prueba",
                modifier = Modifier.weight(1f)
            )
        }

        errores.aceptaTerminos?.let { mensaje ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Error,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )

                Text(
                    text = mensaje,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Vista previa",
                    style = MaterialTheme.typography.titleMedium
                )

                Text("Nombre: ${estado.nombre}")
                Text("Correo: ${estado.correo}")
                Text("Dirección: ${estado.direccion}")

                Text(
                    text = "Términos aceptados: " +
                            if (estado.aceptaTerminos) "Sí" else "No"
                )
            }
        }

        Button(
            onClick = {
                if (usuarioViewModel.validarFormulario()) {
                    onVerResumen()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver resumen")
        }
    }
}