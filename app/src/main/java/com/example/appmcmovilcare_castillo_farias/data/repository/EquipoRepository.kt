package com.example.appmcmovilcare_castillo_farias.data.repository

import com.example.appmcmovilcare_castillo_farias.data.local.dao.EquipoDao
import com.example.appmcmovilcare_castillo_farias.data.local.entity.EquipoEntity
import kotlinx.coroutines.flow.Flow

class EquipoRepository(
    private val equipoDao: EquipoDao
) {

    fun observarEquipos(): Flow<List<EquipoEntity>> {
        return equipoDao.observarEquipos()
    }

    fun observarEquipo(id: String): Flow<EquipoEntity?> {
        return equipoDao.observarEquipo(id)
    }

    suspend fun cargarEquiposDePrueba() {
        val equipos = listOf(
            EquipoEntity(
                id = "EQ001",
                nombre = "Notebook de prueba",
                marca = "Lenovo",
                modelo = "IdeaPad 3",
                numeroSerie = "DEMO-LEN-001",
                estado = "En diagnóstico",
                diagnostico = "Se está revisando una falla de encendido."
            ),
            EquipoEntity(
                id = "EQ002",
                nombre = "Impresora de prueba",
                marca = "Epson",
                modelo = "EcoTank L3250",
                numeroSerie = "DEMO-EPS-002",
                estado = "Lista para entrega",
                diagnostico = "Mantenimiento finalizado. Impresión verificada."
            )
        )

        equipoDao.insertarEquipos(equipos)
    }
}