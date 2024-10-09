package com.aarondeveloper.ticketing.presentation.screens.sistemas

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aarondeveloper.ticketing.data.remote.dao.SistemaDto
import com.aarondeveloper.ticketing.data.repository.SistemaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SistemaViewModel @Inject constructor(
    private val sistemaRepository: SistemaRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getSistemas()
    }

    fun validarCampos(): Boolean {
        return !_uiState.value.nombre.isNullOrBlank()
    }

    fun save() {
        viewModelScope.launch {
            if (!validarCampos()) {
                _uiState.update {
                    it.copy(errorMessage = "Por favor, completa todos los campos correctamente.", guardado = false)
                }
                return@launch
            }

            val existe = _uiState.value.sistemas.firstOrNull { it.nombre == _uiState.value.nombre }

            if (existe != null) {
                _uiState.update {
                    it.copy(errorMessage = "Ya existe un sistema con este nombre.", guardado = false)
                }
                return@launch
            }

            val nuevoSistema = SistemaDto(
                sistemaId = null,
                nombre = _uiState.value.nombre!!
            )
            val response = sistemaRepository.save(nuevoSistema)
            if (response.isSuccessful) {
                _uiState.update { it.copy(errorMessage = null, guardado = true) }
                getSistemas()
            }
            else {
                _uiState.update { it.copy(errorMessage = "Error al guardar el sistema: ${response.message()}", guardado = false) }
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

            if (_uiState.value.sistemaId == null || _uiState.value.sistemaId!! <= 0) {
                _uiState.update {
                    it.copy(errorMessage = "Ha ocurrido un error al tratar de identificar el sistema.", guardado = false)
                }
                return@launch
            }

            val existe = _uiState.value.sistemas.firstOrNull {
                it.nombre == _uiState.value.nombre && it.sistemaId != _uiState.value.sistemaId
            }

            if (existe != null) {
                _uiState.update {
                    it.copy(errorMessage = "Ya existe un sistema con este nombre.", guardado = false)
                }
                return@launch
            }

            val sistemaActualizado = SistemaDto(
                sistemaId = _uiState.value.sistemaId,
                nombre = _uiState.value.nombre!!
            )

            val response = sistemaRepository.update(sistemaActualizado)
            if (response.isSuccessful) {
                _uiState.update { it.copy(errorMessage = null, guardado = true) }
                getSistemas()
            }
            else {
                _uiState.update { it.copy(errorMessage = "Error al actualizar el sistema: ${response.message()}", guardado = false) }
            }
        }
    }


    fun delete() {
        viewModelScope.launch {
            sistemaRepository.delete(_uiState.value.sistemaId!!)
            _uiState.update { it.copy(errorMessage = null, guardado = true) }
        }
    }

    fun selectedSistema(sistemaId: Int) {
        viewModelScope.launch {
            if (sistemaId > 0) {
                val sistema = sistemaRepository.get(sistemaId)
                _uiState.update { currentState ->
                    currentState.copy(
                        sistemaId = sistema?.sistemaId,
                        nombre = sistema?.nombre ?: "",
                        errorMessage = null
                    )
                }
            }
        }
    }

    private fun getSistemas() {
        viewModelScope.launch {
            val sistemas = sistemaRepository.getSistemas()
            _uiState.update {
                it.copy(sistemas = sistemas)
            }
        }
    }

    fun onNombreChange(nombre: String?) {
        _uiState.update {
            it.copy(nombre = nombre)
        }
    }
}

data class UiState(
    val sistemaId: Int? = null,
    val nombre: String? = "",
    val errorMessage: String? = null,
    var guardado: Boolean? = false,
    val sistemas: List<SistemaDto> = emptyList()
)

fun UiState.toEntity() = SistemaDto(
    sistemaId = sistemaId,
    nombre = nombre ?: ""
)