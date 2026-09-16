package com.ctma.prestamolab.data.local

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * 8 Pruebas Instrumentadas para el DAO.
 * Aquí probamos que Room guarde y borre de verdad en el celular.
 */
@RunWith(AndroidJUnit4::class)
class ObjetoPrestamoDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: ObjetoPrestamoDao

    @Before
    fun crearBaseDeDatos() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        // Usamos una base de datos en memoria para que no se guarde basura en el cel
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .setDriver(BundledSQLiteDriver()) // Importante para Room 3.0.3
            .build()
        dao = db.objetoPrestamoDao()
    }

    @After
    fun cerrarBaseDeDatos() {
        db.close()
    }

    @Test
    fun test01_insertar_y_leer_prestamo() = runBlocking {
        val prestamo = ObjetoPrestamo(
            equipoId = "1",
            nombre = "Multimetro",
            categoria = "Electro",
            prestatario = "Juan",
            fechaPrestamo = "15/09/2026",
            estado = "PENDIENTE"
        )
        dao.insertar(prestamo)
        val lista = dao.obtenerTodos().first()
        assertEquals(1, lista.size)
        assertEquals("Multimetro", lista[0].nombre)
    }

    @Test
    fun test02_actualizar_estado_prestamo() = runBlocking {
        val prestamo = ObjetoPrestamo(id = 1, equipoId = "1", nombre = "PC", categoria = "TI", prestatario = "Ana", fechaPrestamo = "10/10/2026", estado = "PENDIENTE")
        dao.insertar(prestamo)
        
        val prestamoActualizado = prestamo.copy(estado = "ENTREGADO")
        dao.actualizar(prestamoActualizado)
        
        val lista = dao.obtenerTodos().first()
        assertEquals("ENTREGADO", lista[0].estado)
    }

    @Test
    fun test03_eliminar_prestamo_por_id() = runBlocking {
        val prestamo = ObjetoPrestamo(id = 10, equipoId = "2", nombre = "Cable", categoria = "TI", prestatario = "Luis", fechaPrestamo = "10/10/2026", estado = "PENDIENTE")
        dao.insertar(prestamo)
        dao.eliminarPorId(10)
        
        val lista = dao.obtenerTodos().first()
        assertTrue(lista.isEmpty())
    }

    @Test
    fun test04_eliminar_todos_los_datos() = runBlocking {
        dao.insertar(ObjetoPrestamo(equipoId = "1", nombre = "A", categoria = "C", prestatario = "P", fechaPrestamo = "F", estado = "E"))
        dao.insertar(ObjetoPrestamo(equipoId = "2", nombre = "B", categoria = "C", prestatario = "P", fechaPrestamo = "F", estado = "E"))
        
        dao.eliminarTodo()
        val lista = dao.obtenerTodos().first()
        assertTrue(lista.isEmpty())
    }

    @Test
    fun test05_ordenar_por_nombre() = runBlocking {
        dao.insertar(ObjetoPrestamo(equipoId = "1", nombre = "Zebra", categoria = "C", prestatario = "P", fechaPrestamo = "F", estado = "E"))
        dao.insertar(ObjetoPrestamo(equipoId = "2", nombre = "Alpha", categoria = "C", prestatario = "P", fechaPrestamo = "F", estado = "E"))
        
        val lista = dao.obtenerOrdenadosPorNombre()
        assertEquals("Alpha", lista[0].nombre)
        assertEquals("Zebra", lista[1].nombre)
    }

    @Test
    fun test06_filtrar_por_nombre() = runBlocking {
        dao.insertar(ObjetoPrestamo(equipoId = "1", nombre = "Laptop Dell", categoria = "C", prestatario = "P", fechaPrestamo = "F", estado = "E"))
        dao.insertar(ObjetoPrestamo(equipoId = "2", nombre = "Mouse HP", categoria = "C", prestatario = "P", fechaPrestamo = "F", estado = "E"))
        
        val resultado = dao.buscarPorNombre("Dell")
        assertEquals(1, resultado.size)
        assertEquals("Laptop Dell", resultado[0].nombre)
    }

    @Test
    fun test07_contar_elementos_despues_de_borrado() = runBlocking {
        dao.insertar(ObjetoPrestamo(id = 1, equipoId = "1", nombre = "A", categoria = "C", prestatario = "P", fechaPrestamo = "F", estado = "E"))
        dao.insertar(ObjetoPrestamo(id = 2, equipoId = "2", nombre = "B", categoria = "C", prestatario = "P", fechaPrestamo = "F", estado = "E"))
        dao.eliminarPorId(1)
        
        val lista = dao.obtenerTodos().first()
        assertEquals(1, lista.size)
        assertEquals(2, lista[0].id)
    }

    @Test
    fun test08_consistencia_datos_multiples() = runBlocking {
        // Insertamos varios y verificamos que el ultimo sea correcto
        for (i in 1..5) {
            dao.insertar(ObjetoPrestamo(equipoId = "$i", nombre = "Objeto $i", categoria = "C", prestatario = "P", fechaPrestamo = "F", estado = "E"))
        }
        val lista = dao.obtenerTodos().first()
        assertEquals(5, lista.size)
        assertTrue(lista.any { it.nombre == "Objeto 5" })
    }
}
