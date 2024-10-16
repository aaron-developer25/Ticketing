package com.aarondeveloper.ticketing.data.remote.remotedatasource

import com.aarondeveloper.ticketing.data.remote.api.TicketingApi
import com.aarondeveloper.ticketing.data.remote.dto.SistemaDto
import javax.inject.Inject

class TicketingDataSource @Inject constructor(
    private val ticketingApi: TicketingApi
) {

    suspend fun getSistemas(): List<SistemaDto> = ticketingApi.getSistemas()

    suspend fun getSistemaById(id: Int) = ticketingApi.getSistemaById(id)

    suspend fun addSistemas(sistemaDto: SistemaDto) = ticketingApi.addSistemas(sistemaDto)

    suspend fun updateSistemas(sistemaDto: SistemaDto) = ticketingApi.updateSistemas(sistemaDto)

    suspend fun deleteSistemas(id: Int) = ticketingApi.deleteSistemas(id)


}
