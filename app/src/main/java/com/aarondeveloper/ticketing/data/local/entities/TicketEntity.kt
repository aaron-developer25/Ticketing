package com.aarondeveloper.ticketing.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Tickets")
data class TicketEntity (
    @PrimaryKey
    val ticketId: Int? = null,
    val prioridadId: Int,
    val fecha: String? = "",
    val cliente: String = "",
    val asunto: String = "",
    val descripcion: String = ""
)