package com.example.uvgenius.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.uvgenius.model.Usuario
import java.time.LocalDate

class AppVM() {
    var usuarioLogeado by mutableStateOf<String?>(null)
        private set


    fun login(user: String, pass: String): Boolean {
        val ok = user.isNotBlank() && pass.isNotBlank()
        if (ok) usuarioLogeado = user
        return ok
    }
}