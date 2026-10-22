package com.example.appmcmovilcare_castillo_farias.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appmcmovilcare_castillo_farias.viewmodel.PreferenciasViewModel

@Composable
fun PreferenciasScreen(
    onVolver: () -> Unit,
    preferenciasViewModel: PreferenciasViewModel = viewModel()
) {
    val estado by preferenciasViewModel.estado.collectAsStateWithLifecycle()

    val colorTarjeta by animateColorAsState(
        targetValue = if (estado.mostrarDiagnosticos) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        },
        animationSpec = tween(400),
        label = "Color de preferencia"
    )

    if (estado.cargando) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Preferencias",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Personaliza cómo consultas la información de tus equipos."
        )

        Card(
            colors = CardDefaults.cardColors(
                containerColor = colorTarjeta,
                contentColor = if (estado.mostrarDiagnosticos) {
                    MaterialTheme.colorScheme.onPrimaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Diagnósticos desplegados",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "Al entrar a Mis equipos, muestra inicialmente " +
                            "los detalles de cada diagnóstico."
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if (estado.mostrarDiagnosticos) {
                            "Activado"
                        } else {
                            "Desactivado"
                        }
                    )

                    Switch(
                        checked = estado.mostrarDiagnosticos,
                        onCheckedChange =
                            preferenciasViewModel::cambiarMostrarDiagnosticos,
                        enabled = !estado.guardando && estado.error == null
                    )
                }
            }
        }

        if (estado.guardando) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )
        }

        AnimatedVisibility(
            visible = estado.mostrarConfirmacion
        ) {
            Text(
                text = "Preferencia guardada correctamente",
                color = MaterialTheme.colorScheme.primary
            )
        }

        estado.error?.let { mensaje ->
            Text(
                text = mensaje,
                color = MaterialTheme.colorScheme.error
            )

            Button(
                onClick = preferenciasViewModel::cargarPreferencias
            ) {
                Text("Reintentar")
            }
        }

        Text(
            text = "La configuración se guarda en este dispositivo.",
            style = MaterialTheme.typography.bodySmall
        )

        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}