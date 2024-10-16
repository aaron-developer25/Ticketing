package com.aarondeveloper.ticketing.data.repository

import com.aarondeveloper.ticketing.data.local.dao.PartidoDao
import com.aarondeveloper.ticketing.data.local.entities.PartidoEntity
import com.aarondeveloper.ticketing.data.remote.dto.PartidoDto
import com.aarondeveloper.ticketing.data.remote.dto.toEntity
import com.aarondeveloper.ticketing.data.remote.remotedatasource.TrackingSportDataSource
import com.aarondeveloper.ticketing.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class PartidoRepository @Inject constructor(
    private val partidoDao: PartidoDao,
    private val trackingSportDataSource: TrackingSportDataSource
) {
    fun getPartidos(): Flow<Resource<List<PartidoEntity>>> = flow {
        try {
            emit(Resource.Loading())
            val partidosR = trackingSportDataSource.getPartidos()
            val partidosL = partidosR.map { it.toEntity() }
            partidosL.forEach { partidoDao.save(it) }

            emit(Resource.Success(partidosL))
        } catch (e: HttpException) {
            emit(Resource.Error("Error de conexión: ${e.message()}"))
        } catch (e: Exception) {
            val partidosL = partidoDao.getAll().firstOrNull()
            if (partidosL.isNullOrEmpty()) {
                emit(Resource.Error("No se encontraron datos locales"))
            } else {
                emit(Resource.Success(partidosL))
            }
        }
    }

    fun getPartido(id: Int): Flow<Resource<PartidoEntity>> = flow {
        try {
            emit(Resource.Loading())
            val partido = partidoDao.find(id)
            if (partido != null) {
                emit(Resource.Success(partido))
            } else {
                emit(Resource.Error("No se encontró el partido"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Error ${e.message}"))
        }
    }

    fun savePartido(partidoDto: PartidoDto): Flow<Resource<PartidoEntity>> = flow {
        try {
            emit(Resource.Loading())
            val partidoR = trackingSportDataSource.addPartidos(partidoDto)
            val partidoL = partidoR.toEntity()
            partidoDao.save(partidoL)
            emit(Resource.Success(partidoL))
        } catch (e: HttpException) {
            emit(Resource.Error("Error de conexión ${e.message()}"))
        } catch (e: Exception) {
            emit(Resource.Error("Error ${e.message}"))
        }
    }

    fun updatePartido(id: Int, partidoDto: PartidoDto): Flow<Resource<PartidoEntity>> = flow {
        try {
            emit(Resource.Loading())
            val partidoR = trackingSportDataSource.updatePartidos(id, partidoDto)
            val partidoL = partidoR.toEntity()
            partidoDao.save(partidoL)
            emit(Resource.Success(partidoL))
        } catch (e: HttpException) {
            emit(Resource.Error("Error de conexión ${e.message()}"))
        } catch (e: Exception) {
            emit(Resource.Error("Error ${e.message}"))
        }
    }

    suspend fun deletePartido(id: Int): Resource<Unit> {
        return try {
            trackingSportDataSource.deletePartidos(id)
            val partido = partidoDao.find(id)
            if (partido != null) {
                partidoDao.delete(partido)
            }
            Resource.Success(Unit)
        } catch (e: HttpException) {
            Resource.Error("Error de conexión ${e.message()}")
        } catch (e: Exception) {
            val partido = partidoDao.find(id)
            if (partido != null) {
                partidoDao.delete(partido)
            }
            Resource.Success(Unit)
        }
    }
}
