package com.aarondeveloper.ticketing.data.remote.remotedatasource

import com.aarondeveloper.ticketing.data.remote.api.TrackingSportApi
import com.aarondeveloper.ticketing.data.remote.dto.PartidoDto
import javax.inject.Inject

class TrackingSportDataSource @Inject constructor(
    private val trackingSportApi: TrackingSportApi
) {

    suspend fun getPartidos(): List<PartidoDto> = trackingSportApi.getPartidos()

    suspend fun getPartidoById(id: Int) = trackingSportApi.getPartidoById(id)

    suspend fun addPartidos(partidoDto: PartidoDto) = trackingSportApi.addPartidos(partidoDto)

    suspend fun updatePartidos(id: Int, partidoDto: PartidoDto) = trackingSportApi.updatePartidos(id, partidoDto)

    suspend fun deletePartidos(id: Int) = trackingSportApi.deletePartidos(id)

}
