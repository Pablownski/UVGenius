package com.example.uvgenius.model

data class HomeUiState(
    val isLoading: Boolean = false,
    val tutorias: List<Tutoria> = emptyList(),
    val error: String? = null
)