package com.aarondeveloper.ticketing.presentation.screens.partidos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aarondeveloper.ticketing.data.local.entities.PartidoEntity
import com.aarondeveloper.ticketing.data.remote.dto.PartidoDto
import com.aarondeveloper.ticketing.data.repository.PartidoRepository
import com.aarondeveloper.ticketing.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartidoViewModel @Inject constructor(
    private val partidoRepository: PartidoRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getPartidos()
    }

    fun validarCampos(): Boolean {
        return !(_uiState.value.titulo.isNullOrBlank() || _uiState.value.descripcion.isNullOrBlank())
    }

    fun save() {
        viewModelScope.launch {
            if (!validarCampos()) {
                _uiState.update {
                    it.copy(errorMessage = "Por favor, completa todos los campos correctamente.", guardado = false)
                }
                return@launch
            }

            val existe = _uiState.value.partidos.firstOrNull { it.titulo == _uiState.value.titulo }

            if (existe != null) {
                _uiState.update {
                    it.copy(errorMessage = "Ya existe un partido con este título.", guardado = false)
                }
                return@launch
            }

            partidoRepository.savePartido(_uiState.value.toDto()).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(guardado = false, errorMessage = null) }
                    }
                    is Resource.Success -> {
                        _uiState.update { it.copy(errorMessage = null, guardado = true) }
                        getPartidos() // Actualizar la lista de partidos después de guardar
                    }
                    is Resource.Error -> {
                        _uiState.update { it.copy(errorMessage = result.message, guardado = false) }
                    }
                }
            }
        }
    }

    fun update() {
        viewModelScope.launch {
            if (!validarCampos()) {
                _uiState.update {
                    it.copy(errorMessage = "Por favor, completa todos los campos correctamente.", guardado = false)
                }
                return@launch
            }

            val partidoId = _uiState.value.partidoId
            if (partidoId == null || partidoId <= 0) {
                _uiState.update {
                    it.copy(errorMessage = "Ha ocurrido un error al tratar de identificar el partido.", guardado = false)
                }
                return@launch
            }

            val existe = _uiState.value.partidos.firstOrNull {
                it.titulo == _uiState.value.titulo && it.partidoId != partidoId
            }

            if (existe != null) {
                _uiState.update {
                    it.copy(errorMessage = "Ya existe un partido con este título.", guardado = false)
                }
                return@launch
            }

            partidoRepository.updatePartido(partidoId, _uiState.value.toDto()).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(guardado = false, errorMessage = null) }
                    }
                    is Resource.Success -> {
                        _uiState.update { it.copy(errorMessage = null, guardado = true) }
                        getPartidos()
                    }
                    is Resource.Error -> {
                        _uiState.update { it.copy(errorMessage = result.message, guardado = false) }
                    }
                }
            }
        }
    }

    fun selectedPartido(partidoId: Int) {
        viewModelScope.launch {
            if (partidoId > 0) {
                partidoRepository.getPartido(partidoId).collect { result ->
                    when (result) {
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            val partido = result.data
                            _uiState.update {
                                it.copy(
                                    partidoId = partido?.partidoId,
                                    titulo = partido?.titulo ?: "",
                                    descripcion = partido?.descripcion ?: "",
                                    errorMessage = null
                                )
                            }
                        }
                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(errorMessage = result.message)
                            }
                        }
                    }
                }
            }
        }
    }

    fun delete() {
        viewModelScope.launch {
            _uiState.value.partidoId?.let { id ->
                val result = partidoRepository.deletePartido(id)
                if (result is Resource.Success) {
                    getPartidos()
                    _uiState.update {
                        it.copy(guardado = true, errorMessage = null)
                    }
                } else if (result is Resource.Error) {
                    _uiState.update {
                        it.copy(errorMessage = result.message)
                    }
                }
            }
        }
    }

    private fun getPartidos() {
        viewModelScope.launch {
            partidoRepository.getPartidos().collect { result ->
                when (result) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(partidos = result.data ?: emptyList())
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(errorMessage = result.message)
                        }
                    }
                }
            }
        }
    }

    fun onTituloChange(titulo: String?) {
        _uiState.update {
            it.copy(titulo = titulo)
        }
    }

    fun onDescripcionChange(descripcion: String?) {
        _uiState.update {
            it.copy(descripcion = descripcion)
        }
    }
}

data class UiState(
    val partidoId: Int? = 0,
    val titulo: String? = "",
    val descripcion: String? = null,
    val errorMessage: String? = null,
    var guardado: Boolean? = false,
    val partidos: List<PartidoEntity> = emptyList()
)

fun UiState.toDto() = PartidoDto(
    partidoId = partidoId ?: 0,
    titulo = titulo ?: "",
    descripcion = descripcion ?: ""
)
