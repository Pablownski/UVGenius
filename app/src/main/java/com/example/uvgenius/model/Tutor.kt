package com.example.uvgenius.model

import android.os.Parcelable
import androidx.annotation.DrawableRes

data class Tutor(
    val id: String,
    val nombre: String,
    val carrera: String,
    val cursos: List<String>,
    val telefono: String,
    val horarios: List<TutoriaSlot>,
    val avatar: Int = android.R.drawable.sym_def_app_icon
)
