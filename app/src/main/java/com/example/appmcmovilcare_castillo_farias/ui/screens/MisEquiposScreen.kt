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
fun MisEquiposScreen(
    onVolver: () -> Unit = {},
    onSolicitarAtencion: () -> Unit = {}
) {
    // La barra superior compartida está en AppNavigation.
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Mis equipos",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Tus equipos registrados",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Datos ficticios para demostrar la navegación.",
                style = MaterialTheme.typography.bodyMedium
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Notebook Lenovo",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text("Código: EQ001")
                    Text("Estado: En revisión")
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
                        text = "Impresora Epson",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text("Código: EQ002")
                    Text("Estado: Disponible")
                }
            }

            Button(
                onClick = onSolicitarAtencion,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Solicitar atención")
            }

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
fun MisEquiposScreenPreview() {
    AppMCmovilCare_Castillo_FariasTheme {
        MisEquiposScreen()
    }
}