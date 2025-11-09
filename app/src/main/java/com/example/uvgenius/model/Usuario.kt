package com.example.uvgenius.model



import androidx.annotation.DrawableRes
import androidx.compose.runtime.snapshots.SnapshotStateList

data class Usuario (
    var id: Int,
    var nombre: String,
    var password: String,
    var carrera: String,
    var cursos: SnapshotStateList<String>,
    var tutorias: SnapshotStateList<Tutoria>,
    var telefono: String,
    var email: String,
    var descripcion: String,
    var horarios: String,
    var avatar: String
)