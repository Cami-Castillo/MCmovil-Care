package com.example.appmcmovilcare_castillo_farias.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.appmcmovilcare_castillo_farias.ui.screens.HomeCompactScreen
import com.example.appmcmovilcare_castillo_farias.ui.screens.HomeExpandedScreen
import com.example.appmcmovilcare_castillo_farias.ui.screens.HomeMediumScreen
import com.example.appmcmovilcare_castillo_farias.ui.theme.AppMCmovilCare_Castillo_FariasTheme

@Composable
fun HomeScreen(
    anchoVentana: WindowWidthSizeClass = WindowWidthSizeClass.Compact,
    onMisEquipos: () -> Unit = {},
    onSolicitarAtencion: () -> Unit = {}
) {
    when (anchoVentana) {
        WindowWidthSizeClass.Compact -> {
            HomeCompactScreen(
                onMisEquipos = onMisEquipos,
                onSolicitarAtencion = onSolicitarAtencion
            )
        }

        WindowWidthSizeClass.Medium -> {
            HomeMediumScreen(
                onMisEquipos = onMisEquipos,
                onSolicitarAtencion = onSolicitarAtencion
            )
        }

        else -> {
            HomeExpandedScreen(
                onMisEquipos = onMisEquipos,
                onSolicitarAtencion = onSolicitarAtencion
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun HomeScreenPreview() {
    AppMCmovilCare_Castillo_FariasTheme {
        HomeScreen()
    }
}