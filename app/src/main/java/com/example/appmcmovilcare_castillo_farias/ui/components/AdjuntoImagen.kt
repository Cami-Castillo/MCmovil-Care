package com.example.appmcmovilcare_castillo_farias.ui.components

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import java.io.File

@Composable
fun AdjuntoImagen(
    imagenRuta: String?,
    procesando: Boolean,
    habilitado: Boolean,
    error: String?,
    onAdjuntar: (Uri) -> Unit,
    onQuitar: () -> Unit,
    onError: (String) -> Unit
) {
    val context = LocalContext.current

    var capturaPendiente by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    val galeria = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            onAdjuntar(uri)
        }
    }

    val camara = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { guardada ->
        val pendiente = capturaPendiente
        capturaPendiente = null

        if (pendiente != null) {
            val uri = Uri.parse(pendiente)

            if (guardada) {
                onAdjuntar(uri)
            } else {
                runCatching {
                    context.contentResolver.delete(uri, null, null)
                }
            }
        }
    }

    fun abrirCamara() {
        try {
            val carpeta = File(context.cacheDir, "capturas")

            check(carpeta.exists() || carpeta.mkdirs())

            val archivo = File.createTempFile(
                "captura_",
                ".jpg",
                carpeta
            )

            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                archivo
            )

            capturaPendiente = uri.toString()
            camara.launch(uri)
        } catch (errorCaptura: Exception) {
            capturaPendiente?.let {
                runCatching {
                    context.contentResolver.delete(
                        Uri.parse(it),
                        null,
                        null
                    )
                }
            }

            capturaPendiente = null
            onError("No se pudo abrir la cámara del dispositivo.")
        }
    }

    val permisoCamara = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) {
            abrirCamara()
        } else {
            onError(
                "Permiso de cámara denegado. " +
                        "Puedes usar la galería o habilitarlo en Ajustes."
            )
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Imagen del equipo · opcional",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "Usa una imagen ficticia o de un objeto de prueba. Máximo 10 MB.",
            style = MaterialTheme.typography.bodySmall
        )

        if (imagenRuta != null) {
            ImagenSolicitud(
                nombreArchivo = imagenRuta
            )

            TextButton(
                onClick = onQuitar,
                enabled = habilitado && !procesando
            ) {
                Text("Quitar imagen")
            }
        }

        if (procesando) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )
            Text("Preparando imagen...")
        }

        OutlinedButton(
            onClick = {
                val concedido = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED

                if (concedido) {
                    abrirCamara()
                } else {
                    permisoCamara.launch(Manifest.permission.CAMERA)
                }
            },
            enabled = habilitado &&
                    !procesando &&
                    capturaPendiente == null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                Icons.Default.CameraAlt,
                contentDescription = null
            )
            Spacer(Modifier.width(8.dp))
            Text("Tomar fotografía")
        }

        OutlinedButton(
            onClick = {
                try {
                    galeria.launch("image/*")
                } catch (errorGaleria: Exception) {
                    onError("No se pudo abrir el selector de imágenes.")
                }
            },
            enabled = habilitado &&
                    !procesando &&
                    capturaPendiente == null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                Icons.Default.PhotoLibrary,
                contentDescription = null
            )
            Spacer(Modifier.width(8.dp))
            Text("Seleccionar desde galería")
        }

        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun ImagenSolicitud(
    nombreArchivo: String
) {
    val context = LocalContext.current

    AsyncImage(
        model = File(
            context.filesDir,
            "adjuntos/$nombreArchivo"
        ),
        contentDescription = "Imagen adjunta del equipo",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}