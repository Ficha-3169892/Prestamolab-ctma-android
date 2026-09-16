package com.ctma.prestamolab.util

/**
 * Clase sencilla para validar que los datos del préstamo estén bien escritos.
 * Como estudiantes, queremos que el usuario no meta información rara.
 */
object ValidadorPrestamo {

    /**
     * Revisa si el texto está vacío o solo tiene espacios.
     */
    fun esTextoValido(texto: String): Boolean {
        return texto.isNotBlank()
    }

    /**
     * Revisa si las horas están en el rango permitido de 1 a 8.
     */
    fun esDuracionValida(horas: Int): Boolean {
        return horas in 1..8
    }

    /**
     * Revisa si el texto es muy largo (más de 50 caracteres).
     */
    fun esTextoDemasiadoLargo(texto: String): Boolean {
        return texto.length > 50
    }

    /**
     * Revisa si el texto contiene caracteres especiales o emojis.
     * (Simplificado: busca si hay algo que no sea letra, número o espacio)
     */
    fun tieneCaracteresEspeciales(texto: String): Boolean {
        val regex = Regex("^[a-zA-Z0-9 ]*$")
        return !regex.matches(texto)
    }

    /**
     * Revisa si la fecha tiene el formato correcto dd/mm/aaaa.
     */
    fun esFormatoFechaValido(fecha: String): Boolean {
        val regex = Regex("^\\d{2}/\\d{2}/\\d{4}$")
        return regex.matches(fecha)
    }
}
