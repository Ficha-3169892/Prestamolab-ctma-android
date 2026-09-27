package com.ctma.prestamolab.data.local

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.ctma.prestamolab.data.model.Equipo

@Entity(tableName = "equipos")
data class EquipoEntity(
    @PrimaryKey
    val id: String,
    val nombre: String,
    val categoria: String,
    val estado: String,
    val ubicacion: String,
    val descripcion: String
) {
    fun toDomain(): Equipo = Equipo(
        id = id,
        nombre = nombre,
        categoria = categoria,
        estado = estado,
        ubicacion = ubicacion,
        descripcion = descripcion
    )

    companion object {
        fun fromDomain(equipo: Equipo): EquipoEntity = EquipoEntity(
            id = equipo.id,
            nombre = equipo.nombre,
            categoria = equipo.categoria,
            estado = equipo.estado,
            ubicacion = equipo.ubicacion,
            descripcion = equipo.descripcion
        )
    }
}
