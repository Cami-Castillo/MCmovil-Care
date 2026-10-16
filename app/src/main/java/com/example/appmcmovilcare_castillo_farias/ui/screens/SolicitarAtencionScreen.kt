package com.example.appmcmovilcare_castillo_farias.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appmcmovilcare_castillo_farias.ui.theme.AppMCmovilCare_Castillo_FariasTheme

@Composable
fun SolicitarAtencionScreen(
    onInicio: () -> Unit = {},
    onMisEquipos: () -> Unit = {},
    onVolver: () -> Unit = {}
) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = onInicio,
                    icon = { Text("I") },
                    label = { Text("Inicio") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = onMisEquipos,
                    icon = { Text("E") },
                    label = { Text("Equipos") }
                )

                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Text("A") },
                    label = { Text("Atención") }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Solicitar atención",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Consulta las opciones de atención para tus equipos.",
                style = MaterialTheme.typography.bodyLarge
            )

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Servicio técnico",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text("Revisión de fallas y diagnóstico del equipo.")
                    Text("Mantenimiento preventivo.")
                    Text("Consulta sobre garantías.")
                }
            }

            Text(
                text = "Esta pantalla es un prototipo. El envío de solicitudes se implementará más adelante.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            OutlinedButton(
                onClick = onVolver,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SolicitarAtencionScreenPreview() {
    AppMCmovilCare_Castillo_FariasTheme {
        SolicitarAtencionScreen()
    }
}