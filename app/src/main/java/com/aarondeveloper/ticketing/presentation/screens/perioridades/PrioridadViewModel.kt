package com.aarondeveloper.ticketing.presentation.screens.perioridades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aarondeveloper.ticketing.data.local.entities.PrioridadEntity
import com.aarondeveloper.ticketing.data.local.repository.PrioridadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrioridadViewModel @Inject constructor(
    private val prioridadRepository: PrioridadRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getPrioridades()
    }

    fun save() {
        viewModelScope.launch {

            if (_uiState.value.descripcion.isNullOrBlank() || _uiState.value.diascompromiso == null || _uiState.value.diascompromiso?.toIntOrNull()!! <= 0) {
                _uiState.update {
                    it.copy(errorMessage = "Por favor, completa todos los campos correctamente.", guardado = false)
                }
                return@launch
            }

            val existe = _uiState.value.prioridades.firstOrNull { it.descripcion == _uiState.value.descripcion }

            if (existe != null) {
                _uiState.update {
                    it.copy(errorMessage = "Ya existe una prioridad con esta descripción.", guardado = false)
                }
                return@launch
            }

            prioridadRepository.save(_uiState.value.toEntity())
            _uiState.update { it.copy(errorMessage = null, guardado = true) }
        }
    }

    fun update() {
        viewModelScope.launch {
            if (_uiState.value.descripcion.isNullOrBlank() ||
                _uiState.value.diascompromiso.isNullOrBlank() ||
                _uiState.value.diascompromiso?.toIntOrNull()!! <= 0) {
                _uiState.update {
                    it.copy(errorMessage = "Por favor, completa todos los campos correctamente.", guardado = false)
                }
                return@launch
            }

            if (_uiState.value.prioridadId == null || _uiState.value.prioridadId!! <= 0) {
                _uiState.update {
                    it.copy(errorMessage = "Ha ocurrido un error al tratar de identificar la prioridad.", guardado = false)
                }
                return@launch
            }

            val existe = _uiState.value.prioridades.firstOrNull {
                it.descripcion == _uiState.value.descripcion && it.prioridadId != _uiState.value.prioridadId
            }

            if (existe != null) {
                _uiState.update {
                    it.copy(errorMessage = "Ya existe una prioridad con esta descripción.", guardado = false)
                }
                return@launch
            }

            prioridadRepository.save(_uiState.value.toEntity())
            _uiState.update { it.copy(errorMessage = null, guardado = true) }
        }
    }

    fun selectedPrioridad(prioridadId: Int) {
        viewModelScope.launch {
            if (prioridadId > 0) {
                val prioridad = prioridadRepository.getPrioridad(prioridadId)
                _uiState.update { currentState ->
                    currentState.copy(
                        prioridadId = prioridad?.prioridadId,
                        descripcion = prioridad?.descripcion ?: "",
                        diascompromiso = prioridad?.diasCompromiso?.toString() ?: "",
                        errorMessage = null
                    )
                }
            }
        }
    }

    fun delete() {
        viewModelScope.launch {
            prioridadRepository.delete(_uiState.value.toEntity())
        }
    }

    private fun getPrioridades() {
        viewModelScope.launch {
            prioridadRepository.getPrioridades().collect { prioridades ->
                _uiState.update {
                    it.copy(prioridades = prioridades)
                }
            }
        }
    }

    fun onDescripcionChange(descripcion: String?) {
        _uiState.update {
            it.copy(descripcion = descripcion)
        }
    }

    fun onDiasCompromisoChange(diascompromiso: String?) {
        _uiState.update {
            it.copy(diascompromiso = diascompromiso)
        }
    }
}

data class UiState(
    val prioridadId: Int? = null,
    val descripcion: String? = "",
    val diascompromiso: String? = null,
    val errorMessage: String? = null,
    var guardado: Boolean? = false,
    val prioridades: List<PrioridadEntity> = emptyList()
)

fun UiState.toEntity() = PrioridadEntity(
    prioridadId = prioridadId,
    descripcion = descripcion ?: "",
    diasCompromiso = diascompromiso?.toIntOrNull() ?: 0
)