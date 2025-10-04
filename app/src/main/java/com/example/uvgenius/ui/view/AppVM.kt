package com.example.uvgenius.ui.view

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import com.example.uvgenius.model.Usuario
import com.example.uvgenius.R
import com.example.uvgenius.model.Tutoria
import kotlin.collections.addAll

// para copiar rapido y hacer pruebas: sal24374@uvg.edu.gt Shampol07#
class AppVM() {
    var usuarioLogeado by mutableStateOf<Usuario?>(null)

    var userList = mutableStateListOf(
        usuarioFrom(
            id = 1,
            nombre = "Juan",
            password = "Shampol07#",
            carrera = "Sistemas",
            cursos = listOf("Cálculo 2", "Física 3", "Plataformas Móviles", "Ecuaciones Diferenciales"),
            tutorias = emptyList(),
            telefono = "5514-2209",
            email = "sal24374@uvg.edu.gt",
            avatar = R.drawable.cuchututor,
            descripcion = "El mejor profe de la UVG",
            horarios = "11:00 - 17:00"
        ),
        usuarioFrom(
            id = 2,
            nombre = "Diego",
            password = "Municipal132",
            carrera = "Sistemas",
            cursos = listOf("Android", "C++ Pthreads"),
            tutorias = emptyList(),
            telefono = "5555-5555",
            email = "diego@uvg.edu.gt",
            avatar = R.drawable.cuchututor,
            descripcion = "Prueba.",
            horarios = "14:00 - 18:00"
        ),
        usuarioFrom(
            id = 3,
            nombre = "Santiago",
            password = "Corderito132",
            carrera = "Química",
            cursos = listOf("Química"),
            tutorias = emptyList(),
            telefono = "5555-0000",
            email = "santi@uvg.edu.gt",
            avatar = R.drawable.cuchututor,
            descripcion = "Químico curioso, profe paciente.",
            horarios = "09:00 - 12:00"
        ),
        usuarioFrom(
            id = 4,
            nombre = "Samuel",
            password = "Samu132",
            carrera = "Biología",
            cursos = listOf("Ciencias de la vida"),
            tutorias = emptyList(),
            telefono = "5555-1111",
            email = "samu@uvg.edu.gt",
            avatar = R.drawable.cuchututor,
            descripcion = "AAAA.",
            horarios = "10:00 - 16:00"
        ),
        usuarioFrom(
            id = 5,
            nombre = "Pablo",
            password = "Pablownski",
            carrera = "Marketing",
            cursos = listOf("Marketing Digital", "Cálculo para el mercadeo"),
            tutorias = emptyList(),
            telefono = "5555-2222",
            email = "pablo@uvg.edu.gt",
            avatar = R.drawable.cuchututor,
            descripcion = "Prueba.",
            horarios = "13:00 - 19:00"
        ),
        usuarioFrom(
            id = 6,
            nombre = "Daniel",
            password = "Danielson132",
            carrera = "Civil Arquitectonica",
            cursos = listOf("Pisos 1", "Pisos Picados 3"),
            tutorias = emptyList(),
            telefono = "5555-3333",
            email = "daniel@uvg.edu.gt",
            avatar = R.drawable.cuchututor,
            descripcion = "Prueba.",
            horarios = "08:00 - 12:00"
        ),
        usuarioFrom(
            id = 7,
            nombre = "Juan",
            password = "Juanito057",
            carrera = "Bioquimica",
            cursos = listOf("Quimica 2", "FisicoQuimica"),
            tutorias = emptyList(),
            telefono = "5555-4444",
            email = "juan@uvg.edu.gt",
            avatar = R.drawable.cuchututor,
            descripcion = "Prueba.",
            horarios = "15:00 - 20:00"
        ),
        usuarioFrom(
            id = 0,
            nombre = "Debug",
            password = "a",
            carrera = "Sistemas",
            cursos = listOf("Cálculo 2", "Física 3", "Plataformas Móviles", "Ecuaciones Diferenciales"),
            tutorias = emptyList(),
            telefono = "5514-2209",
            email = "a",
            avatar = R.drawable.cuchututor,
            descripcion = "El mejor profe de la UVG",
            horarios = "11:00 - 17:00"
        ),
    )

    fun usuarioFrom(
        id: Int,
        nombre: String,
        password: String,
        carrera: String,
        cursos: List<String>,
        tutorias: List<Tutoria>,
        telefono: String,
        email: String,
        avatar: Int,
        descripcion: String = "",
        horarios: String = ""
    ): Usuario {
        return Usuario(
            id = id,
            nombre = nombre,
            password = password,
            carrera = carrera,
            cursos = mutableStateListOf<String>().also { it.addAll(cursos) },
            tutorias = mutableStateListOf<Tutoria>().also { it.addAll(tutorias) },
            telefono = telefono,
            email = email,
            avatar = avatar,
            descripcion = descripcion,
            horarios = horarios
        )
    }

    fun updateUsuarioLogeado(
        password: String,
        carrera: String,
        cursos: List<String>,
        telefono: String,
        email: String,
        descripcion: String,
        horarios: String,
        avatar: Int? = null
    ) {
        usuarioLogeado?.let { u ->
            u.password = password
            u.carrera = carrera

            u.cursos.apply {
                clear()
                addAll(cursos)
            }

            u.telefono = telefono
            u.email = email
            if (avatar != null) u.avatar = avatar
            u.descripcion = descripcion
            u.horarios = horarios

            usuarioLogeado = u
        }
    }

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