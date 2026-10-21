package com.example.appmcmovilcare_castillo_farias.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appmcmovilcare_castillo_farias.data.local.entity.EquipoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EquipoDao {

    @Query("SELECT * FROM equipos ORDER BY nombre ASC")
    fun observarEquipos(): Flow<List<EquipoEntity>>

    @Query("SELECT * FROM equipos WHERE id = :id")
    fun observarEquipo(id: String): Flow<EquipoEntity?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarEquipos(equipos: List<EquipoEntity>)
}