package com.aarondeveloper.ticketing.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aarondeveloper.ticketing.data.local.dao.PartidoDao
import com.aarondeveloper.ticketing.data.local.dao.PrioridadDao
import com.aarondeveloper.ticketing.data.local.dao.TicketDao
import com.aarondeveloper.ticketing.data.local.dao.SistemaDao
import com.aarondeveloper.ticketing.data.local.entities.PartidoEntity
import com.aarondeveloper.ticketing.data.local.entities.PrioridadEntity
import com.aarondeveloper.ticketing.data.local.entities.SistemaEntity
import com.aarondeveloper.ticketing.data.local.entities.TicketEntity

@Database(
    entities = [
        PrioridadEntity::class,
        TicketEntity::class,
        SistemaEntity::class,
        PartidoEntity::class
    ],
    version = 5,
    exportSchema = false
)
abstract class TicketingDB : RoomDatabase() {
    abstract fun prioridadDao(): PrioridadDao
    abstract fun ticketDao(): TicketDao
    abstract fun sistemaDao(): SistemaDao
    abstract fun partidoDao(): PartidoDao
}