package com.aarondeveloper.ticketing.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Prioridades")
data class PrioridadEntity (
    @PrimaryKey
    val PrioridadId: Int? = null,
    var Descripcion: String = "",
    var DiasCompromiso: Int? = null
)