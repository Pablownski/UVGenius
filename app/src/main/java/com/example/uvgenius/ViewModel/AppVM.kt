package com.example.uvgenius.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.uvgenius.model.Tutor
import com.example.uvgenius.model.TutoriaSlot
import java.time.LocalDate

class AppVM : ViewModel() {
    var usuarioLogeado by mutableStateOf<String?>(null)
        private set

    val tutores = listOf(
        Tutor(
            id = "1",
            nombre = "Juan Salguero",
            carrera = "Sistemas",
            cursos = listOf("Cálculo 2", "Física 3", "Plataformas Móviles"),
            telefono = "5514-2209",
            horarios = listOf(
                TutoriaSlot(LocalDate.of(2025,9,6), "11:00", "13:00", "Álgebra Lineal", "Juan"),
                TutoriaSlot(LocalDate.of(2025,9,6), "15:00", "17:00", "Química", "Pablo"),
            )
        ),
        Tutor(
            id = "2",
            nombre = "Diego Gudiel",
            carrera = "Plataformas Móviles",
            cursos = listOf("Android", "C++ Pthreads"),
            telefono = "5555-5555",
            horarios = listOf(
                TutoriaSlot(LocalDate.of(2025,9,7), "09:00", "10:30", "Android", "Diego")
            )
        ),
        Tutor("3", "Santiago", "Química", listOf("Química"), "5555-0000", emptyList()),
        Tutor("4", "Samuel", "Ciencias de la vida", listOf("Biología"), "5555-1111", emptyList())
    )

    fun login(user: String, pass: String): Boolean {
        val ok = user.isNotBlank() && pass.isNotBlank()
        if (ok) usuarioLogeado = user
        return ok
    }
}