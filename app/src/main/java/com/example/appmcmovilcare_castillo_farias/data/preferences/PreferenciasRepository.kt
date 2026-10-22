package com.example.appmcmovilcare_castillo_farias.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.preferenciasDataStore by preferencesDataStore(
    name = "preferencias_mcmovil"
)

class PreferenciasRepository(context: Context) {

    private val appContext = context.applicationContext

    private val mostrarDiagnosticosKey =
        booleanPreferencesKey("mostrar_diagnosticos")

    val mostrarDiagnosticos: Flow<Boolean> =
        appContext.preferenciasDataStore.data.map { preferencias ->
            preferencias[mostrarDiagnosticosKey] ?: true
        }

    suspend fun guardarMostrarDiagnosticos(valor: Boolean) {
        appContext.preferenciasDataStore.edit { preferencias ->
            preferencias[mostrarDiagnosticosKey] = valor
        }
    }
}