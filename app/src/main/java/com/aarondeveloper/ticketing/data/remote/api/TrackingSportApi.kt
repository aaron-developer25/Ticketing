package com.aarondeveloper.ticketing.data.remote.api

import com.aarondeveloper.ticketing.data.remote.dto.PartidoDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TrackingSportApi {

    // Partidos
    @GET("api/Partidos")
    suspend fun getPartidos(): List<PartidoDto>
    @GET("api/Partidos/{id}")
    suspend fun getPartidoById(@Path("id") id: Int): PartidoDto
    @POST("api/Partidos")
    suspend fun addPartidos(@Body partidoDto: PartidoDto): PartidoDto
    @PUT("api/Partidos/{id}")
    suspend fun updatePartidos(@Path("id") id: Int, @Body partidoDto: PartidoDto): PartidoDto
    @DELETE("api/Partidos/{id}")
    suspend fun deletePartidos(@Path("id") id: Int): PartidoDto
}