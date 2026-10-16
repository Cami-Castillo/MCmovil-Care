package com.example.appmcmovilcare_castillo_farias.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.appmcmovilcare_castillo_farias.viewmodel.UsuarioViewModel

@Composable
fun RegistroScreen(
    usuarioViewModel: UsuarioViewModel,
    onVerResumen: () -> Unit
) {
    // La pantalla observa los datos y errores del ViewModel.
    val estado by usuarioViewModel.estado.collectAsState()
    val errores by usuarioViewModel.errores.collectAsState()

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

        OutlinedTextField(
            value = estado.nombre,
            onValueChange = usuarioViewModel::onNombreChange,
            label = { Text("Nombre completo") },
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
            label = { Text("Clave") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            isError = errores.clave != null,
            supportingText = {
                Text(
                    errores.clave
                        ?: "Usa al menos 8 caracteres"
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.direccion,
            onValueChange = usuarioViewModel::onDireccionChange,
            label = { Text("Dirección") },
            minLines = 2,
            isError = errores.direccion != null,
            supportingText = {
                errores.direccion?.let { Text(it) }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = estado.aceptaTerminos,
                onCheckedChange =
                    usuarioViewModel::onAceptarTerminosChange
            )

            Text(
                text = "Acepto los términos del registro",
                modifier = Modifier.weight(1f)
            )
        }

        errores.aceptaTerminos?.let { mensaje ->
            Text(
                text = mensaje,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Vista previa reactiva, como en el ejemplo del profesor.
        HorizontalDivider()

        Text(
            text = "Vista previa en tiempo real",
            style = MaterialTheme.typography.titleMedium
        )

        Text("Nombre: ${estado.nombre}")
        Text("Correo: ${estado.correo}")
        Text("Dirección: ${estado.direccion}")
        Text(
            "Términos aceptados: " +
                    if (estado.aceptaTerminos) "Sí" else "No"
        )

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