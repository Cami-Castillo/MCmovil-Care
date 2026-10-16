package com.example.appmcmovilcare_castillo_farias.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appmcmovilcare_castillo_farias.viewmodel.UsuarioViewModel

@Composable
fun ResumenScreen(
    usuarioViewModel: UsuarioViewModel,
    onEditar: () -> Unit,
    onInicio: () -> Unit
) {
    // Observa el mismo estado que utiliza el formulario.
    val estado by usuarioViewModel.estado.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Resumen del registro",
            style = MaterialTheme.typography.headlineMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Datos ingresados",
                    style = MaterialTheme.typography.titleLarge
                )

                Text("Nombre: ${estado.nombre}")
                Text("Correo: ${estado.correo}")
                Text("Dirección: ${estado.direccion}")

                Text(
                    text = "Clave: " + "•".repeat(estado.clave.length)
                )

                Text(
                    text = "Términos aceptados: " +
                            if (estado.aceptaTerminos) "Sí" else "No"
                )
            }
        }

        Text(
            text = "Este resumen muestra los datos del formulario. " +
                    "Todavía no se ha creado una cuenta en un servidor.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Button(
            onClick = onEditar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Editar datos")
        }

        OutlinedButton(
            onClick = onInicio,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al inicio")
        }
    }
}