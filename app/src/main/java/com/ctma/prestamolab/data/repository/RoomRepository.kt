package com.ctma.prestamolab.data.repository

import com.ctma.prestamolab.data.local.ObjetoPrestamo
import com.ctma.prestamolab.data.local.ObjetoPrestamoDao
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RoomRepository(private val dao: ObjetoPrestamoDao) {

    fun obtenerTodasLasSolicitudes(): Flow<List<ObjetoPrestamo>> = dao.obtenerTodos()

    suspend fun guardarPrestamo(equipoId: String, nombre: String, categoria: String, prestatario: String) {
        val fechaActual = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
        val nuevoPrestamo = ObjetoPrestamo(
            equipoId = equipoId,
            nombre = nombre,
            categoria = categoria,
            prestatario = prestatario,
            fechaPrestamo = fechaActual,
            estado = "PENDIENTE"
        )
        dao.insertar(nuevoPrestamo)
    }

    suspend fun actualizarEstado(objeto: ObjetoPrestamo, nuevoEstado: String) {
        dao.actualizar(objeto.copy(estado = nuevoEstado))
    }

    suspend fun eliminar(id: Int) {
        dao.eliminarPorId(id)
    }

    suspend fun sembrarDatosSiEsNecesario(equiposIniciales: List<com.ctma.prestamolab.data.model.Equipo>) {
        // Podríamos sembrar equipos como préstamos iniciales o simplemente dejarlos en memoria.
        // Por ahora, solo nos aseguramos de que Room tenga algo si está vacío para la prueba.
    }
}
