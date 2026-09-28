package com.ctma.prestamolab.data.repository

import com.ctma.prestamolab.data.local.EquipoDao
import com.ctma.prestamolab.data.local.EquipoEntity
import com.ctma.prestamolab.data.local.ObjetoPrestamo
import com.ctma.prestamolab.data.local.ObjetoPrestamoDao
import com.ctma.prestamolab.data.model.Equipo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RoomRepository(
    private val objetoPrestamoDao: ObjetoPrestamoDao,
    private val equipoDao: EquipoDao
) {

    fun obtenerTodasLasSolicitudes(): Flow<List<ObjetoPrestamo>> = objetoPrestamoDao.obtenerTodos()

    fun obtenerTodosLosEquipos(): Flow<List<Equipo>> {
        return equipoDao.obtenerTodos().map { entidades ->
            entidades.map { it.toDomain() }
        }
    }

    suspend fun guardarPrestamo(equipoId: String, nombre: String, categoria: String, prestatario: String, evidenciaUri: String? = null) {
        val fechaActual = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
        val nuevoPrestamo = ObjetoPrestamo(
            equipoId = equipoId,
            nombre = nombre,
            categoria = categoria,
            prestatario = prestatario,
            fechaPrestamo = fechaActual,
            estado = "PENDIENTE",
            evidenciaUri = evidenciaUri
        )
        objetoPrestamoDao.insertar(nuevoPrestamo)
        equipoDao.actualizarEstado(equipoId, "RESERVADO")
    }

    suspend fun actualizarEstadoSolicitud(
        objeto: ObjetoPrestamo,
        nuevoEstado: String,
        evidenciaDevolucionUri: String? = null,
        adminFeedback: String? = null
    ) {
        val actualizado = objeto.copy(
            estado = nuevoEstado,
            evidenciaDevolucionUri = evidenciaDevolucionUri ?: objeto.evidenciaDevolucionUri,
            adminFeedback = adminFeedback ?: objeto.adminFeedback
        )
        objetoPrestamoDao.actualizar(actualizado)
        if (nuevoEstado == "ENTREGADO" || nuevoEstado == "CANCELADA") {
            equipoDao.actualizarEstado(objeto.equipoId, "DISPONIBLE")
        }
    }

    suspend fun eliminarSolicitud(id: Int, equipoId: String) {
        objetoPrestamoDao.eliminarPorId(id)
        equipoDao.actualizarEstado(equipoId, "DISPONIBLE")
    }

    suspend fun liberarEquipo(equipoId: String) {
        equipoDao.actualizarEstado(equipoId, "DISPONIBLE")
    }

    suspend fun sembrarEquiposSiEstaVacio() {
        if (equipoDao.contarEquipos() == 0) {
            val equiposIniciales = listOf(
                EquipoEntity("1", "Multímetro Digital", "Electrónica", "DISPONIBLE", "Lab 1", "Multímetro de precisión"),
                EquipoEntity("2", "Osciloscopio 100MHz", "Electrónica", "DISPONIBLE", "Lab 2", "Osciloscopio digital 2 canales"),
                EquipoEntity("3", "Impresora 3D Creality", "Prototipado", "DISPONIBLE", "Lab 3", "Impresora FDM para prototipo"),
                EquipoEntity("4", "Kit Arduino Uno", "Robótica", "DISPONIBLE", "Lab 1", "Kit básico de microcontroladores")
            )
            equipoDao.insertarLista(equiposIniciales)
        }
    }
}
