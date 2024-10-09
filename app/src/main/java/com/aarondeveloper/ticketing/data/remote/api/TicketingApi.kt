package com.aarondeveloper.ticketing.data.remote.api

import com.aarondeveloper.ticketing.data.remote.dto.SistemaDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TicketingApi {

    // Sistemas
    @GET("api/Sistemas")
    suspend fun getSistemas(): List<SistemaDto>
    @GET("api/Sistemas/{id}")
    suspend fun getSistemaById(@Path("id") id: Int): SistemaDto
    @POST("api/Sistemas")
    suspend fun addSistemas(@Body sistemaDto: SistemaDto): Response<SistemaDto>
    @PUT("api/Sistemas")
    suspend fun updateSistemas(@Body sistemaDto: SistemaDto): Response<SistemaDto>
    @DELETE("api/Sistemas/{id}")
    suspend fun deleteSistemas(@Path("id") id: Int): Response<SistemaDto>
}