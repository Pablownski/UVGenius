package com.example.uvgenius.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tutor(
    val id: String,
    val nombre: String,
    val carrera: String,
    val cursos: List<String>,
    val telefono: String,
    val horarios: List<TutoriaSlot>,
    @DrawableRes val avatar: Int = android.R.drawable.sym_def_app_icon
) : Parcelable
