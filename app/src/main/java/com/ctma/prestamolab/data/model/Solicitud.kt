package com.ctma.prestamolab.data.model

data class Solicitud(
    val id: String,
    val equipoId: String,
    val equipoNombre: String,
    val ambienteDestino: String,
    val proposito: String,
    val duracionHoras: Int,
    val estado: String = "SOLICITADA"
)