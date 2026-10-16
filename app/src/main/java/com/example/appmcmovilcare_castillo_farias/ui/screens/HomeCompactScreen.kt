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
import com.example.appmcmovilcare_castillo_farias.ui.theme.AppMCmovilCare_Castillo_FariasTheme

@Composable
fun HomeCompactScreen(
    modifier: Modifier = Modifier,
    onMisEquipos: () -> Unit = {},
    onSolicitarAtencion: () -> Unit = {}
) {
    // En una ventana pequeña, el contenido se ordena verticalmente.
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Imagen de MCmovil Care",
            modifier = Modifier.size(100.dp)
        )

        Text(
            text = "Bienvenido a MCmovil Care",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Consulta el estado y cuidado de tus equipos",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // Datos ficticios para el prototipo.
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
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

@Preview(
    name = "Inicio compacto",
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
fun HomeCompactScreenPreview() {
    AppMCmovilCare_Castillo_FariasTheme {
        HomeCompactScreen()
    }
}