package com.aarondeveloper.ticketing.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Tickets")
data class TicketEntity (
    @PrimaryKey
    val TicketId: Int? = null,
    var PrioridadId: Int? = null,
    var Fecha: String? = "",
    var Cliente: String = "",
    var Asunto: String = "",
    var Descripcion: String = ""
)