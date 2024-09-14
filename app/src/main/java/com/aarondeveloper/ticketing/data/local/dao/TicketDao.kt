package com.aarondeveloper.ticketing.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.aarondeveloper.ticketing.data.local.entities.TicketEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketDao {
    @Upsert()
    suspend fun save(ticket: TicketEntity)

    @Query(
        """
        SELECT * 
        FROM Tickets 
        WHERE TicketId=:id  
        LIMIT 1
        """
    )
    suspend fun find(id: Int): TicketEntity?

    @Delete
    suspend fun delete(da: TicketEntity)

    @Query("SELECT * FROM Tickets")
    fun getAll(): Flow<List<TicketEntity>>

}