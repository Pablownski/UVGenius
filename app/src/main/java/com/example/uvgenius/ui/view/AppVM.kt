package com.example.uvgenius.ui.view

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import com.example.uvgenius.model.Usuario
import com.example.uvgenius.R


// para copiar facil sal24374@uvg.edu.gt Shampol07#
class AppVM() {
    var usuarioLogeado by mutableStateOf<Usuario?>(null)

    val userList = listOf(

        Usuario(1, "Juan", "Shampol07#", "Sistemas", listOf("Cálculo 2", "Física 3", "Plataformas Móviles"), emptyList(),"5514-2209", "sal24374@uvg.edu.gt", avatar = R.drawable.cuchututor),
        Usuario(
            2,
            "Diego",
            "Municipal132",
            "Sistemas",
            listOf("Android", "C++ Pthreads"),
            emptyList(),
            "5555-5555",
            "diego@uvg.edu.gt",
            avatar = R.drawable.cuchututor
        ),
        Usuario(3, "Santiago", "Corderito132", "Química", listOf("Química"),emptyList() ,"5555-0000", "santi@uvg.edu.gt", avatar = R.drawable.cuchututor),
        )


    fun login(mail: String, pass: String){
        for (u : Usuario in userList) {
            if (u.email == mail && u.password == pass) {
                usuarioLogeado = u
            }
        }
    }

    fun logout(){
        usuarioLogeado = null
    }

    fun checkLogin(mail: String, pass: String): Boolean {
        for (u : Usuario in userList) {
            if (u.email == mail && u.password == pass) {
                usuarioLogeado = u
                return true
            }
        }
        return false
    }
}