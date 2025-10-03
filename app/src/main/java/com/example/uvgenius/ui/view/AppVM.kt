package com.example.uvgenius.ui.view

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import com.example.uvgenius.model.Usuario
import com.example.uvgenius.R



class AppVM() {
    var usuarioLogeado by mutableStateOf<Usuario?>(null)

    val UserList = listOf(

        Usuario("Juan", "Shampol07#", "Sistemas", listOf("Cálculo 2", "Física 3", "Plataformas Móviles"), emptyList(),"5514-2209", "sal24374@uvg.edu.gt", avatar = R.drawable.cuchututor),
        Usuario(
            "Diego",
            "Municipal132",
            "Sistemas",
            listOf("Android", "C++ Pthreads"),
            emptyList(),
            "5555-5555",
            "diego@uvg.edu.gt",
            avatar = R.drawable.cuchututor
        ),
        Usuario("Santiago", "Corderito132", "Química", listOf("Química"),emptyList() ,"5555-0000", "santi@uvg.edu.gt", avatar = R.drawable.cuchututor),
        Usuario("Samuel", "Samu132", "Biología", listOf("Ciencias de la vida"),emptyList() ,"5555-1111", "samu@uvg.edu.gt", avatar = R.drawable.cuchututor),
        Usuario("Pablo", "Pablownski", "Marketing", listOf("Marketing Digital", "Cálculo para el mercadeo"), emptyList(), "5555-2222", "pablo@uvg.edu.gt", avatar = R.drawable.cuchututor),
        Usuario("Daniel", "Danielson132", "Civil Arquitectonica", listOf("Pisos 1, Pisos Picados 3"),emptyList(),  "5555-3333", "daniel@uvg.edu.gt", avatar = R.drawable.cuchututor),
        Usuario("Juan", "Juanito057", "Bioquimica", listOf("Quimica 2", "FisicoQuimica"),emptyList(), "5555-4444", "juan@uvg.edu.gt", avatar = R.drawable.cuchututor),
    )


    fun login(user: Usuario, pass: String): Boolean {
        return true
    }
}