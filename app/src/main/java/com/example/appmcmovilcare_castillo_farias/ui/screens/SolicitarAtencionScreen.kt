package com.example.appmcmovilcare_castillo_farias.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appmcmovilcare_castillo_farias.ui.components.AdjuntoImagen
import com.example.appmcmovilcare_castillo_farias.ui.components.ImagenSolicitud
import com.example.appmcmovilcare_castillo_farias.validation.SolicitudValidator
import com.example.appmcmovilcare_castillo_farias.viewmodel.SolicitudViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SolicitarAtencionScreen(
    onInicio: () -> Unit = {},
    onMisEquipos: () -> Unit = {},
    onVolver: () -> Unit = {},
    solicitudViewModel: SolicitudViewModel = viewModel()
) {
    val estado by solicitudViewModel.estado.collectAsStateWithLifecycle()
    val ocupado = estado.guardando || estado.procesandoImagen

    when {
        estado.cargando -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        estado.errorCarga != null -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = estado.errorCarga ?: "Error de carga",
                    color = MaterialTheme.colorScheme.error
                )

                Button(onClick = solicitudViewModel::cargarDatos) {
                    Text("Reintentar")
                }

                OutlinedButton(onClick = onVolver) {
                    Text("Volver")
                }
            }
        }

        else -> {
            val formatoFecha = remember {
                SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale.getDefault()
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = "Solicitar atención",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }

                item {
                    Text(
                        "Describe lo que necesita tu equipo. " +
                                "Esta demostración guarda los datos en el dispositivo."
                    )
                }

                item {
                    AnimatedVisibility(
                        visible = estado.solicitudCreadaId != null
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor =
                                    MaterialTheme.colorScheme.primaryContainer
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Solicitud N.º " +
                                        "${estado.solicitudCreadaId ?: ""} " +
                                        "guardada correctamente.",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }

                item {
                    SelectorSolicitud(
                        etiqueta = "Equipo",
                        seleccionado = estado.equipoId,
                        opciones = estado.equipos.map {
                            it.id to "${it.nombre} · ${it.id}"
                        },
                        error = estado.errores.equipo,
                        habilitado = !ocupado,
                        icono = {
                            Icon(
                                Icons.Default.Computer,
                                contentDescription = null
                            )
                        },
                        onSeleccionar = solicitudViewModel::cambiarEquipo
                    )
                }

                item {
                    SelectorSolicitud(
                        etiqueta = "Tipo de servicio",
                        seleccionado = estado.servicio,
                        opciones = SolicitudValidator.servicios.map {
                            it to it
                        },
                        error = estado.errores.servicio,
                        habilitado = !ocupado,
                        icono = {
                            Icon(
                                Icons.Default.Build,
                                contentDescription = null
                            )
                        },
                        onSeleccionar = solicitudViewModel::cambiarServicio
                    )
                }

                item {
                    OutlinedTextField(
                        value = estado.descripcion,
                        onValueChange =
                            solicitudViewModel::cambiarDescripcion,
                        enabled = !ocupado,
                        label = { Text("Descripción del problema") },
                        placeholder = {
                            Text("Ejemplo: el equipo no enciende.")
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Description,
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            if (estado.errores.descripcion != null) {
                                Icon(
                                    Icons.Default.Error,
                                    contentDescription =
                                        "Error en la descripción"
                                )
                            }
                        },
                        isError = estado.errores.descripcion != null,
                        supportingText = {
                            Text(
                                estado.errores.descripcion
                                    ?: "Entre 15 y 1000 caracteres"
                            )
                        },
                        minLines = 3,
                        maxLines = 6,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    OutlinedTextField(
                        value = estado.correo,
                        onValueChange = solicitudViewModel::cambiarCorreo,
                        enabled = !ocupado,
                        label = { Text("Correo de contacto") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Email,
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            if (estado.errores.correo != null) {
                                Icon(
                                    Icons.Default.Error,
                                    contentDescription = "Error en el correo"
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        singleLine = true,
                        isError = estado.errores.correo != null,
                        supportingText = {
                            Text(
                                estado.errores.correo
                                    ?: "Usa un correo ficticio para la prueba"
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    AdjuntoImagen(
                        imagenRuta = estado.imagenRuta,
                        procesando = estado.procesandoImagen,
                        habilitado = !estado.guardando,
                        error = estado.errorImagen,
                        onAdjuntar = solicitudViewModel::adjuntarImagen,
                        onQuitar = solicitudViewModel::quitarImagen,
                        onError = solicitudViewModel::mostrarErrorImagen
                    )
                }

                item {
                    estado.errorGuardado?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    Button(
                        onClick = solicitudViewModel::enviarSolicitud,
                        enabled = !ocupado &&
                                estado.equipos.isNotEmpty(),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            if (estado.guardando) {
                                "Guardando..."
                            } else {
                                "Registrar solicitud"
                            }
                        )
                    }
                }

                item {
                    HorizontalDivider()

                    Text(
                        text = "Mis solicitudes",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

                if (estado.solicitudes.isEmpty()) {
                    item {
                        Text("Todavía no has registrado solicitudes.")
                    }
                }

                items(
                    items = estado.solicitudes,
                    key = { it.id }
                ) { solicitud ->
                    var expandida by rememberSaveable(solicitud.id) {
                        mutableStateOf(false)
                    }

                    val equipo = estado.equipos.find {
                        it.id == solicitud.equipoId
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Solicitud N.º ${solicitud.id}",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(equipo?.nombre ?: solicitud.equipoId)
                            Text(solicitud.tipoServicio)

                            Text(
                                text = "Estado: ${solicitud.estado}",
                                color = MaterialTheme.colorScheme.primary
                            )

                            Text(
                                formatoFecha.format(
                                    Date(solicitud.fechaCreacion)
                                )
                            )

                            TextButton(
                                onClick = { expandida = !expandida }
                            ) {
                                Text(
                                    if (expandida) {
                                        "Ocultar detalles"
                                    } else {
                                        "Ver detalles"
                                    }
                                )
                            }

                            AnimatedVisibility(visible = expandida) {
                                Column(
                                    verticalArrangement =
                                        Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(solicitud.descripcion)
                                    Text(
                                        "Contacto: ${solicitud.correoContacto}"
                                    )

                                    solicitud.imagenRuta?.let {
                                        ImagenSolicitud(nombreArchivo = it)
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    OutlinedButton(
                        onClick = onMisEquipos,
                        enabled = !ocupado,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Mis equipos")
                    }

                    OutlinedButton(
                        onClick = onInicio,
                        enabled = !ocupado,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Inicio")
                    }

                    TextButton(
                        onClick = onVolver,
                        enabled = !ocupado,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Volver")
                    }
                }
            }
        }
    }
}

@Composable
private fun SelectorSolicitud(
    etiqueta: String,
    seleccionado: String,
    opciones: List<Pair<String, String>>,
    error: String?,
    habilitado: Boolean,
    icono: @Composable () -> Unit,
    onSeleccionar: (String) -> Unit
) {
    var abierto by remember { mutableStateOf(false) }

    val textoSeleccionado = opciones.find {
        it.first == seleccionado
    }?.second

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = etiqueta,
            style = MaterialTheme.typography.labelLarge
        )

        Box {
            OutlinedButton(
                onClick = { abierto = true },
                enabled = habilitado && opciones.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            ) {
                icono()
                Spacer(Modifier.width(8.dp))

                Text(
                    text = textoSeleccionado ?: "Seleccionar",
                    modifier = Modifier.weight(1f)
                )

                if (error != null) {
                    Icon(
                        Icons.Default.Error,
                        contentDescription = error,
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            DropdownMenu(
                expanded = abierto && habilitado,
                onDismissRequest = { abierto = false }
            ) {
                opciones.forEach { (valor, texto) ->
                    DropdownMenuItem(
                        text = { Text(texto) },
                        onClick = {
                            abierto = false
                            onSeleccionar(valor)
                        }
                    )
                }
            }
        }

        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}