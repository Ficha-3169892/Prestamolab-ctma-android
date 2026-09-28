package com.ctma.prestamolab.data.remote.dto

import com.ctma.prestamolab.data.local.ObjetoPrestamo
import com.ctma.prestamolab.data.model.Solicitud

data class SolicitudDto(
    val id: String,
    val equipoId: String,
    val equipoNombre: String,
    val ambienteDestino: String,
    val proposito: String,
    val duracionHoras: Int,
    val estado: String
) {
    fun toDomain(): Solicitud = Solicitud(
        id = id,
        equipoId = equipoId,
        equipoNombre = equipoNombre,
        ambienteDestino = ambienteDestino,
        proposito = proposito,
        duracionHoras = duracionHoras,
        estado = estado
    )

    fun toEntity(): ObjetoPrestamo = ObjetoPrestamo(
        equipoId = equipoId,
        nombre = equipoNombre,
        categoria = proposito,
        prestatario = ambienteDestino,
        fechaPrestamo = "",
        estado = estado
    )
}
