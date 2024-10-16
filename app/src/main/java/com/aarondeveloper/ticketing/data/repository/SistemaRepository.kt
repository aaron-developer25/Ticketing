package com.aarondeveloper.ticketing.data.repository

import com.aarondeveloper.ticketing.data.remote.remotedatasource.TicketingDataSource
import com.aarondeveloper.ticketing.data.remote.dto.SistemaDto
import javax.inject.Inject

class SistemaRepository @Inject constructor(
    private val ticketingDataSource: TicketingDataSource
) {
    suspend fun getSistemas(): List<SistemaDto> = ticketingDataSource.getSistemas()
    suspend fun get(id: Int): SistemaDto = ticketingDataSource.getSistemaById(id)
    suspend fun save(sistemaDto: SistemaDto) = ticketingDataSource.addSistemas(sistemaDto)
    suspend fun update(sistemaDto: SistemaDto) = ticketingDataSource.updateSistemas(sistemaDto)
    suspend fun delete(id: Int) = ticketingDataSource.deleteSistemas(id)
}