package com.aarondeveloper.ticketing.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.aarondeveloper.ticketing.data.local.entities.PrioridadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PrioridadDao {
    @Upsert
    suspend fun save(prioridad: PrioridadEntity)

    @Query("SELECT * FROM Prioridades WHERE Descripcion = :descripcion LIMIT 1")
    suspend fun findByDescripcion(descripcion: String): PrioridadEntity?

    @Query(
        """
        SELECT * 
        FROM Prioridades 
        WHERE PrioridadId = :id  
        LIMIT 1
        """
    )
    suspend fun find(id: Int): PrioridadEntity?

    @Delete
    suspend fun delete(prioridad: PrioridadEntity)

    @Query("SELECT * FROM Prioridades")
    fun getAll(): Flow<List<PrioridadEntity>>

}