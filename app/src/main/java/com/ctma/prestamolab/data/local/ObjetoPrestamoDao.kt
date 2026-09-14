package com.ctma.prestamolab.data.local

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ObjetoPrestamoDao {
    @Query("SELECT * FROM objetos_prestamo")
    fun obtenerTodos(): Flow<List<ObjetoPrestamo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(objeto: ObjetoPrestamo)

    @Update
    suspend fun actualizar(objeto: ObjetoPrestamo)

    @Query("DELETE FROM objetos_prestamo WHERE id = :id")
    suspend fun eliminarPorId(id: Int)
}
