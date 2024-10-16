package com.aarondeveloper.ticketing.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.aarondeveloper.ticketing.data.local.dao.PartidoDao
import com.aarondeveloper.ticketing.data.local.entities.PartidoEntity
import com.aarondeveloper.ticketing.data.remote.dto.PartidoDto
import com.aarondeveloper.ticketing.data.remote.dto.toEntity
import com.aarondeveloper.ticketing.data.remote.remotedatasource.TrackingSportDataSource
import com.aarondeveloper.ticketing.utils.Resource
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.HttpException

class PartidoRepositoryTest {

    @Test
    fun `getPartidos debería devolver un flujo de Partidos`() = runTest {
        // Given
        val partidosRemotos = listOf(
            PartidoDto(1, "Partido 2024", "Mexico vs Cuba"),
            PartidoDto(2, "Partido 2025", "España vs Francia")
        )
        val partidosLocales = partidosRemotos.map { it.toEntity() }

        val fuenteDatosRemota = mockk<TrackingSportDataSource>()
        val partidoDao = mockk<PartidoDao>()
        val repositorio = PartidoRepository(partidoDao, fuenteDatosRemota)

        coEvery { fuenteDatosRemota.getPartidos() } returns partidosRemotos
        coEvery { partidoDao.save(any()) } just Runs

        // When
        repositorio.getPartidos().test {
            // Then
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()
            val recursoExitoso = awaitItem() as Resource.Success
            Truth.assertThat(recursoExitoso.data).isEqualTo(partidosLocales)

            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { partidoDao.save(any()) }
    }

    @Test
    fun `getPartidos deberia devolver datos locales cuando falla la conexion remota`() = runTest {
        // Given
        val partidosLocales = listOf(
            PartidoEntity(1, "Local Partido 1", "Descripción 1"),
            PartidoEntity(2, "Local Partido 2", "Descripción 2")
        )

        val fuenteDatosRemota = mockk<TrackingSportDataSource>()
        val partidoDao = mockk<PartidoDao>()
        val repositorio = PartidoRepository(partidoDao, fuenteDatosRemota)

        coEvery { fuenteDatosRemota.getPartidos() } throws HttpException(mockk(relaxed = true))
        coEvery { partidoDao.getAll() } returns flowOf(partidosLocales)

        // When
        repositorio.getPartidos().test {
            // Then
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()
            val recursoExitoso = awaitItem() as Resource.Success
            Truth.assertThat(recursoExitoso.data).isEqualTo(partidosLocales)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getPartido debería devolver el partido si existe`() = runTest {
        // Given
        val partidoEntity = PartidoEntity(1, "Partido Existente", "Descripción del partido")

        val fuenteDatosRemota = mockk<TrackingSportDataSource>()
        val partidoDao = mockk<PartidoDao>()
        val repositorio = PartidoRepository(partidoDao, fuenteDatosRemota)

        coEvery { partidoDao.find(1) } returns partidoEntity

        // When
        repositorio.getPartido(1).test {
            // Then
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()
            val recursoExitoso = awaitItem() as Resource.Success
            Truth.assertThat(recursoExitoso.data).isEqualTo(partidoEntity)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { partidoDao.find(1) }
    }

    @Test
    fun `getPartido debería devolver un error si el partido no existe`() = runTest {
        // Given
        val fuenteDatosRemota = mockk<TrackingSportDataSource>()
        val partidoDao = mockk<PartidoDao>()
        val repositorio = PartidoRepository(partidoDao, fuenteDatosRemota)

        coEvery { partidoDao.find(1) } returns null

        // When
        repositorio.getPartido(1).test {
            // Then
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()
            val recursoError = awaitItem() as Resource.Error
            Truth.assertThat(recursoError.message).isEqualTo("No se encontró el partido")
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { partidoDao.find(1) }
    }

    @Test
    fun `savePartido debería guardar y devolver el partido`() = runTest {
        // Given
        val partidoDto = PartidoDto(1, "Nuevo Partido", "Descripción del nuevo partido")
        val partidoEntity = partidoDto.toEntity()

        val fuenteDatosRemota = mockk<TrackingSportDataSource>()
        val partidoDao = mockk<PartidoDao>()
        val repositorio = PartidoRepository(partidoDao, fuenteDatosRemota)

        coEvery { fuenteDatosRemota.addPartidos(partidoDto) } returns partidoDto
        coEvery { partidoDao.save(partidoEntity) } just Runs

        // When
        repositorio.savePartido(partidoDto).test {
            // Then
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()
            val recursoExitoso = awaitItem() as Resource.Success
            Truth.assertThat(recursoExitoso.data).isEqualTo(partidoEntity)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { fuenteDatosRemota.addPartidos(partidoDto) }
        coVerify(exactly = 1) { partidoDao.save(partidoEntity) }
    }

    @Test
    fun `updatePartido debería actualizar y devolver el partido`() = runTest {
        // Given
        val partidoDto = PartidoDto(1, "Partido Actualizado", "Descripción actualizada")
        val partidoEntity = partidoDto.toEntity()

        val fuenteDatosRemota = mockk<TrackingSportDataSource>()
        val partidoDao = mockk<PartidoDao>()
        val repositorio = PartidoRepository(partidoDao, fuenteDatosRemota)

        coEvery { fuenteDatosRemota.updatePartidos(1, partidoDto) } returns partidoDto
        coEvery { partidoDao.save(partidoEntity) } just Runs

        // When
        repositorio.updatePartido(1, partidoDto).test {
            // Then
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()
            val recursoExitoso = awaitItem() as Resource.Success
            Truth.assertThat(recursoExitoso.data).isEqualTo(partidoEntity)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { fuenteDatosRemota.updatePartidos(1, partidoDto) }
        coVerify(exactly = 1) { partidoDao.save(partidoEntity) }
    }

}
