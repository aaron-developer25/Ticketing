package com.aarondeveloper.ticketing.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aarondeveloper.ticketing.data.local.dao.PrioridadDao
import com.aarondeveloper.ticketing.data.local.dao.TicketDao
import com.aarondeveloper.ticketing.data.local.entities.PrioridadEntity
import com.aarondeveloper.ticketing.data.local.entities.TicketEntity

@Database(
    entities = [
        PrioridadEntity::class,
        TicketEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class TicketingDB : RoomDatabase() {
    abstract fun prioridadDao(): PrioridadDao
    abstract fun ticketDao(): TicketDao
}