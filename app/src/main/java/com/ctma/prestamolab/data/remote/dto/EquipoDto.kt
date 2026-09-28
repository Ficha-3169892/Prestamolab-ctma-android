package com.ctma.prestamolab.data.remote.dto

import com.ctma.prestamolab.data.local.EquipoEntity
import com.ctma.prestamolab.data.model.Equipo

data class EquipoDto(
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

    fun toEntity(): EquipoEntity = EquipoEntity(
        id = id,
        nombre = nombre,
        categoria = categoria,
        estado = estado,
        ubicacion = ubicacion,
        descripcion = descripcion
    )
}
