package com.ctma.prestamolab.data.local

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "objetos_prestamo")
data class ObjetoPrestamo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val equipoId: String,
    val nombre: String,
    val categoria: String,
    val prestatario: String,
    val fechaPrestamo: String,
    val estado: String
)
