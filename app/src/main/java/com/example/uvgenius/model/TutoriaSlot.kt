package com.example.uvgenius.model


import java.time.LocalDate

data class TutoriaSlot(
    val fecha: LocalDate,
    val inicio: String,
    val fin: String,
    val materia: String,
    val con: String
)
