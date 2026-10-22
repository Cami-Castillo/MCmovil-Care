package com.example.appmcmovilcare_castillo_farias.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appmcmovilcare_castillo_farias.R

@Composable
fun HomeExpandedScreen(
    modifier: Modifier = Modifier,
    onMisEquipos: () -> Unit = {},
    onSolicitarAtencion: () -> Unit = {}
) {
    // Distribución para ventanas grandes.
    Row(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(32.dp),
        horizontalArrangement = Arrangement.spacedBy(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Image(
                painter = painterResource(
                    R.drawable.logo_mcmovil
                ),
                contentDescription = "Imagen de MCmovil Care",
                modifier = Modifier.size(200.dp)
            )

            Text(
                text = "Bienvenido a MCmovil Care",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Consulta el estado y cuidado de tus equipos",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Resumen de tus equipos",
                style = MaterialTheme.typography.headlineSmall
            )

            // Datos ficticios para el prototipo.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "2",
                            style = MaterialTheme.typography.headlineLarge
                        )

                        Text("Equipos")
                    }
                }

                Card(
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "1",
                            style = MaterialTheme.typography.headlineLarge
                        )

                        Text("Pendiente")
                    }
                }
            }

            Button(
                onClick = onMisEquipos,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mis equipos")
            }

            Button(
                onClick = onSolicitarAtencion,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Solicitar atención")
            }
        }
    }
}

@Preview(
    name = "Inicio expandido",
    showBackground = true,
    widthDp = 1000,
    heightDp = 700
)
@Composable
fun HomeExpandedScreenPreview() {
    MaterialTheme {
        HomeExpandedScreen()
    }
}