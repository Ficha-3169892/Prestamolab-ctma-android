package com.ctma.prestamolab.data.remote

import com.ctma.prestamolab.data.remote.dto.EquipoDto
import com.ctma.prestamolab.data.remote.dto.SolicitudDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PrestamoApiService {

    @GET("api/equipos")
    suspend fun obtenerEquipos(): Response<List<EquipoDto>>

    @GET("api/solicitudes")
    suspend fun obtenerSolicitudes(): Response<List<SolicitudDto>>

    @POST("api/solicitudes")
    suspend fun crearSolicitud(@Body solicitud: SolicitudDto): Response<SolicitudDto>

    @POST("api/solicitudes/{id}/cancelar")
    suspend fun cancelarSolicitud(@Path("id") id: String): Response<Unit>
}
