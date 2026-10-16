package com.example.appmcmovilcare_castillo_farias

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.appmcmovilcare_castillo_farias.navigation.AppNavigation
import com.example.appmcmovilcare_castillo_farias.ui.theme.AppMCmovilCare_Castillo_FariasTheme
import com.example.appmcmovilcare_castillo_farias.ui.utils.calcularAnchoVentana

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppMCmovilCare_Castillo_FariasTheme {
                val anchoVentana = calcularAnchoVentana(this@MainActivity)

                AppNavigation(
                    anchoVentana = anchoVentana
                )
            }
        }
    }
}