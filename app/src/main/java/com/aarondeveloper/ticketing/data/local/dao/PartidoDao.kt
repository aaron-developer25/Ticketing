package com.aarondeveloper.ticketing.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.aarondeveloper.ticketing.data.local.entities.PartidoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PartidoDao {
    @Upsert
    suspend fun save(partido: PartidoEntity)

    @Query("SELECT * FROM Partidos WHERE titulo = :titulo LIMIT 1")
    suspend fun findByDescripcion(titulo: String): PartidoEntity?

    @Query(
        """
        SELECT * 
        FROM Partidos 
        WHERE partidoId = :id  
        LIMIT 1
        """
    )

    suspend fun find(id: Int): PartidoEntity?

    @Delete
    suspend fun delete(partido: PartidoEntity)

    @Query("SELECT * FROM Partidos")
    fun getAll(): Flow<List<PartidoEntity>>

}