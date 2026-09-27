package com.ctma.prestamolab.ui.state

import com.ctma.prestamolab.data.model.Equipo
import com.ctma.prestamolab.data.model.Solicitud

sealed interface PrestamoUiState {
    data object Cargando : PrestamoUiState

    data class Exito(
        val equipos: List<Equipo> = emptyList(),
        val solicitudes: List<Solicitud> = emptyList(),
        val mensajeErrorFormulario: String? = null,
        val prestatarioDefault: String = "",
        val ambienteDefault: String = ""
    ) : PrestamoUiState

    data object Vacio : PrestamoUiState

    data class Error(val mensaje: String) : PrestamoUiState
}
