package com.aarondeveloper.ticketing.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Partidos")
data class PartidoEntity (
    @PrimaryKey
    val partidoId: Int,
    val titulo: String= "",
    val descripcion: String= ""
)