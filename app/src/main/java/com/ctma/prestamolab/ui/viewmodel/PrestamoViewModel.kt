package com.ctma.prestamolab.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ctma.prestamolab.data.model.Equipo
import com.ctma.prestamolab.data.model.Solicitud
import com.ctma.prestamolab.data.repository.InMemoryRepository

class PrestamoViewModel(
    private val repository: InMemoryRepository = InMemoryRepository()
) : ViewModel() {

    var equipos = mutableStateListOf<Equipo>()
        private set

    var solicitudes = mutableStateListOf<Solicitud>()
        private set

    var mensajeError by mutableStateOf<String?>(null)
        private set

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        equipos.clear()
        equipos.addAll(repository.obtenerEquipos())
        solicitudes.clear()
        solicitudes.addAll(repository.obtenerSolicitudes())
    }

    fun solicitarPrestamo(
        equipo: Equipo,
        ambienteDestino: String,
        proposito: String,
        duracionHoras: Int
    ): Boolean {
        if (ambienteDestino.isBlank() || proposito.isBlank()) {
            mensajeError = "Todos los campos son obligatorios"
            return false
        }
        if (duracionHoras !in 1..8) {
            mensajeError = "La duración debe ser entre 1 y 8 horas"
            return false
        }

        val nuevaSolicitud = Solicitud(
            id = (solicitudes.size + 1).toString(),
            equipoId = equipo.id,
            equipoNombre = equipo.nombre,
            ambienteDestino = ambienteDestino,
            proposito = proposito,
            duracionHoras = duracionHoras,
            estado = "PENDIENTE"
        )

        repository.guardarSolicitud(nuevaSolicitud)
        repository.actualizarEstadoEquipo(equipo.id, "EN_USO")
        cargarDatos()
        mensajeError = null
        return true
    }

    fun marcarComoEntregado(solicitud: Solicitud) {
        repository.actualizarEstadoSolicitud(solicitud.id, "ENTREGADO")
        repository.actualizarEstadoEquipo(solicitud.equipoId, "DISPONIBLE")
        cargarDatos()
    }

    fun cancelarSolicitud(solicitud: Solicitud) {
        repository.actualizarEstadoSolicitud(solicitud.id, "CANCELADO")
        repository.actualizarEstadoEquipo(solicitud.equipoId, "DISPONIBLE")
        cargarDatos()
    }

    fun limpiarError() {
        mensajeError = null
    }
}