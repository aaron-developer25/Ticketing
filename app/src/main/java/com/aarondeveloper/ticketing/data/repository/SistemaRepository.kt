package com.aarondeveloper.ticketing.data.repository

import com.aarondeveloper.ticketing.data.remote.api.TicketingApi
import com.aarondeveloper.ticketing.data.remote.dao.SistemaDto
import javax.inject.Inject

class SistemaRepository @Inject constructor(
    private val ticketingApi: TicketingApi
) {
    suspend fun getSistemas(): List<SistemaDto> = ticketingApi.getSistemas()
    suspend fun get(id: Int): SistemaDto = ticketingApi.getSistemaById(id)
    suspend fun save(sistemaDto: SistemaDto) = ticketingApi.addSistemas(sistemaDto)
    suspend fun update(sistemaDto: SistemaDto) = ticketingApi.updateSistemas(sistemaDto)
    suspend fun delete(id: Int) = ticketingApi.deleteSistemas(id)
}