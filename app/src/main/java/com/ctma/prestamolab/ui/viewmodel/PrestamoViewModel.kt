package com.ctma.prestamolab.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ctma.prestamolab.data.local.ObjetoPrestamo
import com.ctma.prestamolab.data.model.Equipo
import com.ctma.prestamolab.data.model.Solicitud
import com.ctma.prestamolab.data.repository.InMemoryRepository
import com.ctma.prestamolab.data.repository.RoomRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PrestamoViewModel(
    private val inMemoryRepository: InMemoryRepository = InMemoryRepository(),
    private val roomRepository: RoomRepository? = null
) : ViewModel() {

    var equipos = mutableStateListOf<Equipo>()
        private set

    var solicitudes = mutableStateListOf<Solicitud>()
        private set

    var mensajeError by mutableStateOf<String?>(null)
        private set

    init {
        cargarCatalogo()
        observarSolicitudes()
    }

    private fun cargarCatalogo() {
        equipos.clear()
        equipos.addAll(inMemoryRepository.obtenerEquipos())
    }

    private fun observarSolicitudes() {
        if (roomRepository == null) {
            // Fallback para modo diseño o si no se provee repo
            solicitudes.clear()
            solicitudes.addAll(inMemoryRepository.obtenerSolicitudes())
            return
        }

        viewModelScope.launch {
            roomRepository.obtenerTodasLasSolicitudes().collectLatest { listaLocal ->
                solicitudes.clear()
                solicitudes.addAll(listaLocal.map { it.toSolicitud() })
            }
        }
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

        viewModelScope.launch {
            if (roomRepository != null) {
                roomRepository.guardarPrestamo(
                    equipoId = equipo.id,
                    nombre = equipo.nombre,
                    categoria = equipo.categoria,
                    prestatario = "$ambienteDestino ($proposito)"
                )
            } else {
                // Fallback in memory
                val nuevaSolicitud = Solicitud(
                    id = (solicitudes.size + 1).toString(),
                    equipoId = equipo.id,
                    equipoNombre = equipo.nombre,
                    ambienteDestino = ambienteDestino,
                    proposito = proposito,
                    duracionHoras = duracionHoras,
                    estado = "PENDIENTE"
                )
                inMemoryRepository.guardarSolicitud(nuevaSolicitud)
            }
            inMemoryRepository.actualizarEstadoEquipo(equipo.id, "EN_USO")
            cargarCatalogo()
        }

        mensajeError = null
        return true
    }

    fun marcarComoEntregado(solicitud: Solicitud) {
        viewModelScope.launch {
            if (roomRepository != null) {
                // Buscamos el objeto original para actualizarlo
                // Como mapeamos ID de String a Int, intentamos convertirlo
                val idInt = solicitud.id.toIntOrNull()
                if (idInt != null) {
                    val actual = solicitudesRaw.find { it.id == idInt }
                    if (actual != null) {
                        roomRepository.actualizarEstado(actual, "ENTREGADO")
                    }
                }
            }
            inMemoryRepository.actualizarEstadoEquipo(solicitud.equipoId, "DISPONIBLE")
            cargarCatalogo()
        }
    }

    private var solicitudesRaw = listOf<ObjetoPrestamo>()
    
    // Extensión para mapear
    private fun ObjetoPrestamo.toSolicitud(): Solicitud {
        // Guardamos la lista raw para poder actualizar luego por ID
        synchronized(this@PrestamoViewModel) {
            val currentRaw = solicitudesRaw.toMutableList()
            currentRaw.removeAll { it.id == this.id }
            currentRaw.add(this)
            solicitudesRaw = currentRaw
        }

        return Solicitud(
            id = this.id.toString(),
            equipoId = this.equipoId,
            equipoNombre = this.nombre,
            ambienteDestino = this.prestatario,
            proposito = this.categoria,
            duracionHoras = 1,
            estado = this.estado
        )
    }

    fun cancelarSolicitud(solicitud: Solicitud) {
        viewModelScope.launch {
            if (roomRepository != null) {
                val idInt = solicitud.id.toIntOrNull()
                if (idInt != null) {
                    roomRepository.eliminar(idInt)
                }
            }
            inMemoryRepository.actualizarEstadoEquipo(solicitud.equipoId, "DISPONIBLE")
            cargarCatalogo()
        }
    }

    fun limpiarError() {
        mensajeError = null
    }
}
