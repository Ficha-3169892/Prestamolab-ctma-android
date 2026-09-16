package com.ctma.prestamolab.util

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * 12 Pruebas Unitarias para el Validador.
 * Aquí probamos que las reglas de negocio funcionen sin prender el celular.
 */
class ValidadorPrestamoTest {

    // --- Pruebas de Texto Vacío ---

    @Test
    fun `test 01 - texto normal es valido`() {
        assertTrue(ValidadorPrestamo.esTextoValido("Laboratorio 1"))
    }

    @Test
    fun `test 02 - texto vacio no es valido`() {
        assertFalse(ValidadorPrestamo.esTextoValido(""))
    }

    @Test
    fun `test 03 - texto con solo espacios no es valido`() {
        assertFalse(ValidadorPrestamo.esTextoValido("   "))
    }

    // --- Pruebas de Duración (1 a 8 horas) ---

    @Test
    fun `test 04 - duracion de 5 horas es valida`() {
        assertTrue(ValidadorPrestamo.esDuracionValida(5))
    }

    @Test
    fun `test 05 - duracion de 0 horas no es valida`() {
        assertFalse(ValidadorPrestamo.esDuracionValida(0))
    }

    @Test
    fun `test 06 - duracion negativa no es valida`() {
        assertFalse(ValidadorPrestamo.esDuracionValida(-1))
    }

    @Test
    fun `test 07 - duracion mayor a 8 no es valida`() {
        assertFalse(ValidadorPrestamo.esDuracionValida(9))
    }

    // --- Pruebas de Longitud ---

    @Test
    fun `test 08 - texto corto no es demasiado largo`() {
        assertFalse(ValidadorPrestamo.esTextoDemasiadoLargo("Hola"))
    }

    @Test
    fun `test 09 - texto de mas de 50 letras es demasiado largo`() {
        val textoLargo = "Este es un texto que definitivamente tiene mas de cincuenta caracteres para probar el limite"
        assertTrue(ValidadorPrestamo.esTextoDemasiadoLargo(textoLargo))
    }

    // --- Pruebas de Caracteres Especiales ---

    @Test
    fun `test 10 - nombre con emojis tiene caracteres especiales`() {
        assertTrue(ValidadorPrestamo.tieneCaracteresEspeciales("Juan 🚀"))
    }

    // --- Pruebas de Formato de Fecha ---

    @Test
    fun `test 11 - fecha con formato correcto es valida`() {
        assertTrue(ValidadorPrestamo.esFormatoFechaValido("15/09/2026"))
    }

    @Test
    fun `test 12 - fecha con formato raro no es valida`() {
        assertFalse(ValidadorPrestamo.esFormatoFechaValido("2026-09-15"))
    }
}
