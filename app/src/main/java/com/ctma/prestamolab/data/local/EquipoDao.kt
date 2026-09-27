package com.ctma.prestamolab.data.local

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EquipoDao {

    @Query("SELECT * FROM equipos")
    fun obtenerTodos(): Flow<List<EquipoEntity>>

    @Query("SELECT * FROM equipos WHERE id = :id")
    suspend fun obtenerPorId(id: String): EquipoEntity?

    @Query("SELECT COUNT(*) FROM equipos")
    suspend fun contarEquipos(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarLista(equipos: List<EquipoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(equipo: EquipoEntity)

    @Update
    suspend fun actualizar(equipo: EquipoEntity)

    @Query("UPDATE equipos SET estado = :nuevoEstado WHERE id = :id")
    suspend fun actualizarEstado(id: String, nuevoEstado: String)
}
