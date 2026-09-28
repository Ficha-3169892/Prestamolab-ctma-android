package com.ctma.prestamolab.data.remote

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PrestamoApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: PrestamoApiService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PrestamoApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `obtenerEquipos retorna lista de equipos cuando HTTP 200`() = runBlocking {
        // Arrange
        val jsonResponse = """
            [
                {
                    "id": "1",
                    "nombre": "Multímetro",
                    "categoria": "Electrónica",
                    "estado": "DISPONIBLE",
                    "ubicacion": "Lab 1",
                    "descripcion": "Multímetro digital"
                }
            ]
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(jsonResponse)
        )

        // Act
        val response = apiService.obtenerEquipos()

        // Assert
        assertTrue(response.isSuccessful)
        val body = response.body()
        assertNotNull(body)
        assertEquals(1, body!!.size)
        assertEquals("Multímetro", body[0].nombre)
    }

    @Test
    fun `obtenerEquipos retorna error cuando HTTP 500`() = runBlocking {
        // Arrange
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        // Act
        val response = apiService.obtenerEquipos()

        // Assert
        assertEquals(false, response.isSuccessful)
        assertEquals(500, response.code())
    }
}
