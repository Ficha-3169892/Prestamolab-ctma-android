package com.ctma.prestamolab.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ctma.prestamolab.data.datastore.UserPreferencesRepository
import com.ctma.prestamolab.data.local.ObjetoPrestamo
import com.ctma.prestamolab.data.model.Equipo
import com.ctma.prestamolab.data.model.Solicitud
import com.ctma.prestamolab.data.repository.InMemoryRepository
import com.ctma.prestamolab.data.repository.RoomRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PrestamoViewModel(
    private val inMemoryRepository: InMemoryRepository = InMemoryRepository(),
    private val roomRepository: RoomRepository? = null,
    private val userPreferencesRepository: UserPreferencesRepository? = null
) : ViewModel() {

    var equipos = mutableStateListOf<Equipo>()
        private set

    var solicitudes = mutableStateListOf<Solicitud>()
        private set

    var mensajeError by mutableStateOf<String?>(null)
        private set

    init {
        inicializarDatos()
        observarEquipos()
        observarSolicitudes()
    }

    private fun inicializarDatos() {
        if (roomRepository != null) {
            viewModelScope.launch {
                roomRepository.sembrarEquiposSiEstaVacio()
            }
        }
    }

    private fun observarEquipos() {
        if (roomRepository == null) {
            equipos.clear()
            equipos.addAll(inMemoryRepository.obtenerEquipos())
            return
        }

        viewModelScope.launch {
            roomRepository.obtenerTodosLosEquipos().collectLatest { listaEquipos ->
                equipos.clear()
                equipos.addAll(listaEquipos)
            }
        }
    }

    private fun observarSolicitudes() {
        if (roomRepository == null) {
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
        if (proposito.length !in 10..180) {
            mensajeError = "El propósito debe tener entre 10 y 180 caracteres"
            return false
        }
        if (duracionHoras !in 1..8) {
            mensajeError = "La duración debe ser entre 1 y 8 horas"
            return false
        }

        viewModelScope.launch {
            userPreferencesRepository?.guardarPreferencias(
                prestatario = proposito,
                ambiente = ambienteDestino
            )

            if (roomRepository != null) {
                roomRepository.guardarPrestamo(
                    equipoId = equipo.id,
                    nombre = equipo.nombre,
                    categoria = equipo.categoria,
                    prestatario = "$ambienteDestino ($proposito)"
                )
            } else {
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
                inMemoryRepository.actualizarEstadoEquipo(equipo.id, "EN_USO")
                equipos.clear()
                equipos.addAll(inMemoryRepository.obtenerEquipos())
            }
        }

        mensajeError = null
        return true
    }

    fun marcarComoEntregado(solicitud: Solicitud) {
        viewModelScope.launch {
            if (roomRepository != null) {
                val idInt = solicitud.id.toIntOrNull()
                if (idInt != null) {
                    val actual = solicitudesRaw.find { it.id == idInt }
                    if (actual != null) {
                        roomRepository.actualizarEstadoSolicitud(actual, "ENTREGADO")
                    }
                }
            } else {
                inMemoryRepository.actualizarEstadoEquipo(solicitud.equipoId, "DISPONIBLE")
                equipos.clear()
                equipos.addAll(inMemoryRepository.obtenerEquipos())
            }
        }
    }

    private var solicitudesRaw = listOf<ObjetoPrestamo>()

    private fun ObjetoPrestamo.toSolicitud(): Solicitud {
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
                    roomRepository.eliminarSolicitud(idInt, solicitud.equipoId)
                }
            } else {
                inMemoryRepository.actualizarEstadoEquipo(solicitud.equipoId, "DISPONIBLE")
                equipos.clear()
                equipos.addAll(inMemoryRepository.obtenerEquipos())
            }
        }
    }

    fun limpiarError() {
        mensajeError = null
    }
}
