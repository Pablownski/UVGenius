package com.example.uvgenius.model

import androidx.annotation.DrawableRes

data class Usuario(
    val nombre: String,
    val password: String,
    val carrera: String,
    var cursos: List<String>,
    val telefono: String,
    val email: String,
    @DrawableRes val avatar: Int
)
