package com.ctma.prestamolab.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ctma.prestamolab.data.datastore.UserPreferencesRepository
import com.ctma.prestamolab.data.local.ObjetoPrestamo
import com.ctma.prestamolab.data.model.Equipo
import com.ctma.prestamolab.data.model.Solicitud
import com.ctma.prestamolab.data.repository.InMemoryRepository
import com.ctma.prestamolab.data.repository.RoomRepository
import com.ctma.prestamolab.ui.state.PrestamoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PrestamoViewModel(
    private val inMemoryRepository: InMemoryRepository = InMemoryRepository(),
    private val roomRepository: RoomRepository? = null,
    private val userPreferencesRepository: UserPreferencesRepository? = null
) : ViewModel() {

    private val _mensajeErrorFormulario = MutableStateFlow<String?>(null)

    val sessionEmail: StateFlow<String?> = userPreferencesRepository?.sessionEmail?.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    ) ?: MutableStateFlow(null)

    val sessionRole: StateFlow<String?> = userPreferencesRepository?.sessionRole?.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    ) ?: MutableStateFlow(null)

    init {
        if (roomRepository != null) {
            viewModelScope.launch {
                roomRepository.sembrarEquiposSiEstaVacio()
            }
        }
    }

    val uiState: StateFlow<PrestamoUiState> = if (roomRepository != null) {
        combine(
            roomRepository.obtenerTodosLosEquipos(),
            roomRepository.obtenerTodasLasSolicitudes(),
            _mensajeErrorFormulario
        ) { listaEquipos, listaSolicitudesLocal, errorFormulario ->
            val solicitudesMapeadas = listaSolicitudesLocal.map { it.toSolicitud() }
            if (listaEquipos.isEmpty()) {
                PrestamoUiState.Vacio
            } else {
                PrestamoUiState.Exito(
                    equipos = listaEquipos,
                    solicitudes = solicitudesMapeadas,
                    mensajeErrorFormulario = errorFormulario
                )
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PrestamoUiState.Cargando
        )
    } else {
        MutableStateFlow(
            PrestamoUiState.Exito(
                equipos = inMemoryRepository.obtenerEquipos(),
                solicitudes = inMemoryRepository.obtenerSolicitudes(),
                mensajeErrorFormulario = _mensajeErrorFormulario.value
            )
        )
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
            estado = this.estado,
            evidenciaUri = this.evidenciaUri,
            evidenciaDevolucionUri = this.evidenciaDevolucionUri,
            adminFeedback = this.adminFeedback
        )
    }

    fun iniciarSesion(email: String, role: String) {
        viewModelScope.launch {
            userPreferencesRepository?.guardarSesion(email, role)
        }
    }

    fun cerrarSesion() {
        viewModelScope.launch {
            userPreferencesRepository?.cerrarSesion()
        }
    }

    fun solicitarPrestamo(
        equipo: Equipo,
        ambienteDestino: String,
        proposito: String,
        duracionHoras: Int,
        evidenciaUri: String? = null
    ): Boolean {
        if (ambienteDestino.isBlank() || proposito.isBlank()) {
            _mensajeErrorFormulario.value = "Todos los campos son obligatorios"
            return false
        }
        if (proposito.length !in 10..180) {
            _mensajeErrorFormulario.value = "El propósito debe tener entre 10 y 180 caracteres"
            return false
        }
        if (duracionHoras !in 1..8) {
            _mensajeErrorFormulario.value = "La duración debe ser entre 1 y 8 horas"
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
                    prestatario = "$ambienteDestino ($proposito)",
                    evidenciaUri = evidenciaUri
                )
            } else {
                val nuevaSolicitud = Solicitud(
                    id = System.currentTimeMillis().toString(),
                    equipoId = equipo.id,
                    equipoNombre = equipo.nombre,
                    ambienteDestino = ambienteDestino,
                    proposito = proposito,
                    duracionHoras = duracionHoras,
                    estado = "PENDIENTE"
                )
                inMemoryRepository.guardarSolicitud(nuevaSolicitud)
                inMemoryRepository.actualizarEstadoEquipo(equipo.id, "EN_USO")
            }
        }

        _mensajeErrorFormulario.value = null
        return true
    }

    fun marcarComoEntregado(solicitud: Solicitud, evidenciaDevolucionUri: String? = null) {
        viewModelScope.launch {
            if (roomRepository != null) {
                val idInt = solicitud.id.toIntOrNull()
                if (idInt != null) {
                    val actual = solicitudesRaw.find { it.id == idInt }
                    if (actual != null) {
                        roomRepository.actualizarEstadoSolicitud(actual, "ENTREGADO", evidenciaDevolucionUri = evidenciaDevolucionUri)
                    }
                }
            } else {
                inMemoryRepository.actualizarEstadoEquipo(solicitud.equipoId, "DISPONIBLE")
            }
        }
    }

    fun actualizarAdminFeedback(solicitud: Solicitud, feedback: String) {
        viewModelScope.launch {
            if (roomRepository != null) {
                val idInt = solicitud.id.toIntOrNull()
                if (idInt != null) {
                    val actual = solicitudesRaw.find { it.id == idInt }
                    if (actual != null) {
                        roomRepository.actualizarEstadoSolicitud(actual, actual.estado, adminFeedback = feedback)
                    }
                }
            }
        }
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
            }
        }
    }

    fun crearEquipo(nombre: String, categoria: String, ubicacion: String, descripcion: String): Boolean {
        if (nombre.isBlank() || categoria.isBlank() || ubicacion.isBlank()) return false
        viewModelScope.launch {
            if (roomRepository != null) {
                val nuevoEquipo = com.ctma.prestamolab.data.local.EquipoEntity(
                    id = System.currentTimeMillis().toString(),
                    nombre = nombre,
                    categoria = categoria,
                    estado = "DISPONIBLE",
                    ubicacion = ubicacion,
                    descripcion = descripcion
                )
                roomRepository.guardarEquipo(nuevoEquipo)
            }
        }
        return true
    }

    fun limpiarError() {
        _mensajeErrorFormulario.value = null
    }
}
