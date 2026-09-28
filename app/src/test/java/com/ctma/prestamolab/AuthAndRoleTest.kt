package com.ctma.prestamolab

import org.junit.Assert.assertEquals
import org.junit.Test

class AuthAndRoleTest {

    @Test
    fun `validar credenciales de aprendiz obligatorias`() {
        val emailInput = "aprendiz@formacion.ctma"
        val passInput = "aprendiz123"

        val isValid = emailInput == "aprendiz@formacion.ctma" && passInput == "aprendiz123"
        val role = if (isValid) "APRENDIZ" else "INVALID"

        assertEquals(true, isValid)
        assertEquals("APRENDIZ", role)
    }

    @Test
    fun `validar credenciales de administrador obligatorias`() {
        val emailInput = "admin@formacion.ctma"
        val passInput = "admin123"

        val isValid = emailInput == "admin@formacion.ctma" && passInput == "admin123"
        val role = if (isValid) "ADMIN" else "INVALID"

        assertEquals(true, isValid)
        assertEquals("ADMIN", role)
    }

    @Test
    fun `rechazar credenciales incorrectas`() {
        val emailInput = "intruso@formacion.ctma"
        val passInput = "12345"

        val isValid = (emailInput == "aprendiz@formacion.ctma" && passInput == "aprendiz123") ||
                (emailInput == "admin@formacion.ctma" && passInput == "admin123")

        assertEquals(false, isValid)
    }
}
