package com.aarondeveloper.ticketing.presentation.screens.tickets


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aarondeveloper.ticketing.data.local.entities.PrioridadEntity
import com.aarondeveloper.ticketing.data.local.entities.TicketEntity
import com.aarondeveloper.ticketing.data.local.repository.PrioridadRepository
import com.aarondeveloper.ticketing.data.local.repository.TicketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val prioridadRepository: PrioridadRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTickets()
        getPrioridades()
    }

    fun save() {
        viewModelScope.launch {
            if (_uiState.value.descripcion.isNullOrBlank() ||
                _uiState.value.cliente.isNullOrBlank() ||
                _uiState.value.asunto.isNullOrBlank() ||
                _uiState.value.fecha.isNullOrBlank() ||
                _uiState.value.prioridadId == null) {
                _uiState.update {
                    it.copy(errorMessage = "Por favor, completa todos los campos correctamente.", guardado = false)
                }
                return@launch
            }

            val ticket = _uiState.value.toEntity()
            ticketRepository.save(ticket)
            _uiState.update { it.copy(errorMessage = null, guardado = true) }
        }
    }

    fun update() {
        viewModelScope.launch {
            if (_uiState.value.descripcion.isNullOrBlank() ||
                _uiState.value.cliente.isNullOrBlank() ||
                _uiState.value.asunto.isNullOrBlank() ||
                _uiState.value.fecha.isNullOrBlank() ||
                _uiState.value.prioridadId == null ||
                _uiState.value.ticketId == null) {
                _uiState.update {
                    it.copy(errorMessage = "Por favor, completa todos los campos correctamente.", guardado = false)
                }
                return@launch
            }

            val ticket = _uiState.value.toEntity()
            ticketRepository.save(ticket)
            _uiState.update { it.copy(errorMessage = null, guardado = true) }
        }
    }

    fun selectedTicket(ticketId: Int) {
        viewModelScope.launch {
            if (ticketId > 0) {
                val ticket = ticketRepository.getTicket(ticketId)
                _uiState.update { currentState ->
                    currentState.copy(
                        ticketId = ticket?.TicketId,
                        prioridadId = ticket?.PrioridadId,
                        fecha = ticket?.Fecha ?: "",
                        cliente = ticket?.Cliente ?: "",
                        asunto = ticket?.Asunto ?: "",
                        descripcion = ticket?.Descripcion ?: "",
                        errorMessage = null
                    )
                }
            }
        }
    }

    fun delete() {
        viewModelScope.launch {
            if (_uiState.value.ticketId != null) {
                ticketRepository.delete(_uiState.value.toEntity())
                _uiState.update { it.copy(ticketId = null) }
            }
        }
    }

    private fun getTickets() {
        viewModelScope.launch {
            ticketRepository.getTickets().collect { tickets ->
                _uiState.update {
                    it.copy(tickets = tickets)
                }
            }
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

    fun onClienteChange(cliente: String?) {
        _uiState.update {
            it.copy(cliente = cliente)
        }
    }

    fun onAsuntoChange(asunto: String?) {
        _uiState.update {
            it.copy(asunto = asunto)
        }
    }

    fun onPrioridadIdChange(prioridadId: Int?) {
        _uiState.update {
            it.copy(prioridadId = prioridadId)
        }
    }

    fun onFechaChange(fecha: String?) {
        _uiState.update {
            it.copy(fecha = fecha)
        }
    }
}

data class UiState(
    val ticketId: Int? = null,
    val prioridadId: Int? = null,
    val fecha: String? = "",
    val cliente: String? = null,
    val asunto: String? = null,
    val descripcion: String? = "",
    val errorMessage: String? = null,
    var guardado: Boolean? = false,
    val tickets: List<TicketEntity> = emptyList(),
    val prioridades: List<PrioridadEntity> = emptyList()
)

fun UiState.toEntity() = TicketEntity(
    TicketId = ticketId,
    PrioridadId = prioridadId,
    Fecha = fecha ?: "",
    Cliente = cliente ?: "",
    Asunto = asunto ?: "",
    Descripcion = descripcion ?: ""
)
