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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appmcmovilcare_castillo_farias.R
import com.example.appmcmovilcare_castillo_farias.ui.theme.AppMCmovilCare_Castillo_FariasTheme

@Composable
fun HomeMediumScreen(
    modifier: Modifier = Modifier,
    onMisEquipos: () -> Unit = {},
    onSolicitarAtencion: () -> Unit = {}
) {
    // En una ventana mediana, usamos dos columnas.
    Row(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "Imagen de MCmovil Care",
                modifier = Modifier.size(160.dp)
            )
        }

        Column(
            modifier = Modifier.weight(2f),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Bienvenido a MCmovil Care",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Consulta el estado y cuidado de tus equipos",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Datos ficticios para el prototipo.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Equipos: 2")
                Text("Pendientes: 1")
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
    name = "Inicio mediano",
    showBackground = true,
    widthDp = 700,
    heightDp = 600
)
@Composable
fun HomeMediumScreenPreview() {
    AppMCmovilCare_Castillo_FariasTheme {
        HomeMediumScreen()
    }
}