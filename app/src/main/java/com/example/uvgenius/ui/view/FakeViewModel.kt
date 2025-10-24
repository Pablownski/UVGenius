package com.example.uvgenius.ui.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uvgenius.data.FakeRepository
import com.example.uvgenius.model.Tutoria
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class FakeTutoriaUIState {
    object Loading : FakeTutoriaUIState()
    data class Success(val data: List<Tutoria>) : FakeTutoriaUIState()
    data class Error(val message: String) : FakeTutoriaUIState()
}

class FakeTutoriaViewModel(
    private val repository: FakeRepository = FakeRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<FakeTutoriaUIState>(FakeTutoriaUIState.Loading)
    val uiState: StateFlow<FakeTutoriaUIState> = _uiState

    fun cargarTutorias() {
        viewModelScope.launch {
            // Reinicia el estado para limpiar mensajes anteriores
            _uiState.value = FakeTutoriaUIState.Loading

            repository.getTutorias().collect { result ->
                result.onSuccess { data ->
                    if (data.isNotEmpty()) {
                        _uiState.value = FakeTutoriaUIState.Success(data)
                    } else {
                        // Si viene vacía, trátala como error
                        _uiState.value = FakeTutoriaUIState.Error("No se encontraron tutorías.")
                    }
                }.onFailure { e ->
                    _uiState.value = FakeTutoriaUIState.Error(e.message ?: "Error desconocido")
                }
            }
        }
    }
}
