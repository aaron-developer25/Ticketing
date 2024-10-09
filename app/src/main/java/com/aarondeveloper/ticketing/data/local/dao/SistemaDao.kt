package com.aarondeveloper.ticketing.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.aarondeveloper.ticketing.data.local.entities.SistemaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SistemaDao {
    @Upsert
    suspend fun save(sistema: SistemaEntity)

    @Query(
        """
        SELECT * 
        FROM Sistemas 
        WHERE sistemaId = :id  
        LIMIT 1
        """
    )
    suspend fun find(id: Int): SistemaEntity?

    @Delete
    suspend fun delete(sistema: SistemaEntity)

    @Query("SELECT * FROM Sistemas")
    fun getAll(): Flow<List<SistemaEntity>>

}