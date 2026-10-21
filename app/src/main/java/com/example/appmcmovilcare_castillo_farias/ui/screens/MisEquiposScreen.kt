package com.example.appmcmovilcare_castillo_farias.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appmcmovilcare_castillo_farias.data.local.entity.EquipoEntity
import com.example.appmcmovilcare_castillo_farias.viewmodel.EquiposViewModel

@Composable
fun MisEquiposScreen(
    onVolver: () -> Unit = {},
    onSolicitarAtencion: () -> Unit = {},
    equiposViewModel: EquiposViewModel = viewModel()
) {
    val estado by equiposViewModel.estado.collectAsStateWithLifecycle()

    when {
        estado.cargando -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        estado.error != null -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = estado.error ?: "Error al cargar",
                    color = MaterialTheme.colorScheme.error
                )

                Button(
                    onClick = equiposViewModel::cargarEquipos
                ) {
                    Text("Reintentar")
                }

                OutlinedButton(onClick = onVolver) {
                    Text("Volver")
                }
            }
        }

        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = "Mis equipos",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

                item {
                    Text(
                        text = "Consulta el estado y diagnóstico de tus equipos.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                if (estado.equipos.isEmpty()) {
                    item {
                        Text("Todavía no tienes equipos registrados.")
                    }
                }

                items(
                    items = estado.equipos,
                    key = { equipo -> equipo.id }
                ) { equipo ->
                    TarjetaEquipo(equipo = equipo)
                }

                item {
                    Button(
                        onClick = onSolicitarAtencion,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Solicitar atención")
                    }
                }

                item {
                    OutlinedButton(
                        onClick = onVolver,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Text("Volver")
                    }
                }
            }
        }
    }
}

@Composable
private fun TarjetaEquipo(
    equipo: EquipoEntity
) {
    var expandido by rememberSaveable(equipo.id) {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = equipo.nombre,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "${equipo.marca} · ${equipo.modelo}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Estado: ${equipo.estado}",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )

            TextButton(
                onClick = {
                    expandido = !expandido
                }
            ) {
                Text(
                    text = if (expandido) {
                        "Ocultar diagnóstico"
                    } else {
                        "Ver diagnóstico"
                    }
                )
            }

            AnimatedVisibility(visible = expandido) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Código: ${equipo.id}")
                    Text("Serie: ${equipo.numeroSerie}")

                    Text(
                        text = "Diagnóstico",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(equipo.diagnostico)
                }
            }
        }
    }
}