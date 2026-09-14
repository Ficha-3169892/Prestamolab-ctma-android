package com.ctma.prestamolab.data.repository

import com.ctma.prestamolab.data.model.Equipo
import com.ctma.prestamolab.data.model.Solicitud

class InMemoryRepository {

    private val listaEquipos = mutableListOf(
        Equipo("1", "Multímetro Digital", "Electrónica", "DISPONIBLE", "Lab 1", "Tarea 1"),
        Equipo("2", "Osciloscopio 100MHz", "Electrónica", "DISPONIBLE", "Lab 2", "Tarea 2"),
        Equipo("3", "Impresora 3D Creality", "Prototipado", "EN_USO", "Lab 3", "Tarea 3"),
        Equipo("4", "Kit Arduino Uno", "Robótica", "DISPONIBLE", "Lab 1", "Tarea 4")
    )

    private val listaSolicitudes = mutableListOf<Solicitud>()

    fun obtenerEquipos(): List<Equipo> = listaEquipos

    fun obtenerSolicitudes(): List<Solicitud> = listaSolicitudes

    fun guardarSolicitud(solicitud: Solicitud) {
        listaSolicitudes.add(solicitud)
    }

    fun actualizarEstadoEquipo(equipoId: String, nuevoEstado: String) {
        val index = listaEquipos.indexOfFirst { it.id == equipoId }
        if (index != -1) {
            val equipo = listaEquipos[index]
            listaEquipos[index] = equipo.copy(estado = nuevoEstado)
        }
    }

    fun actualizarEstadoSolicitud(solicitudId: String, nuevoEstado: String) {
        val index = listaSolicitudes.indexOfFirst { it.id == solicitudId }
        if (index != -1) {
            val solicitud = listaSolicitudes[index]
            listaSolicitudes[index] = solicitud.copy(estado = nuevoEstado)
        }
    }
}