package com.aarondeveloper.ticketing.data.remote.dto

import com.aarondeveloper.ticketing.data.local.entities.PartidoEntity

data class PartidoDto(
    val partidoId: Int? = 0,
    val titulo: String = "",
    val descripcion: String = ""
)

fun PartidoDto.toEntity(): PartidoEntity {
    return PartidoEntity(
        partidoId = this.partidoId ?: 0,
        titulo = this.titulo,
        descripcion = this.descripcion
    )
}
