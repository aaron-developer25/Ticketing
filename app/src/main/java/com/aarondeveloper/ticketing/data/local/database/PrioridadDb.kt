package com.aarondeveloper.ticketing.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aarondeveloper.ticketing.data.local.dao.PrioridadDao
import com.aarondeveloper.ticketing.data.local.entities.PrioridadEntity

@Database(
    entities = [
        PrioridadEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PrioridadDb : RoomDatabase() {
    abstract fun prioridadDao(): PrioridadDao
}