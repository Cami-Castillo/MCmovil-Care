package com.example.appmcmovilcare_castillo_farias.ui.utils

import android.app.Activity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable

// Calcula si la ventana tiene un ancho compacto, mediano o expandido.
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun calcularAnchoVentana(activity: Activity): WindowWidthSizeClass {
    return calculateWindowSizeClass(activity).widthSizeClass
}