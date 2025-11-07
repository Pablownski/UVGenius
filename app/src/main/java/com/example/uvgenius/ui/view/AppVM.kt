package com.example.uvgenius.ui.view

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uvgenius.R
import com.example.uvgenius.model.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppVM : ViewModel() {

    var userList = mutableStateListOf<Usuario>()
    var usuarioLogeado: Usuario? = null

    private val db = FirebaseDatabase.getInstance()
    private val refUsuarios = db.getReference("usuarios")

    fun cargarUsuarios(onComplete: (() -> Unit)? = null) {
        refUsuarios.get().addOnSuccessListener { snapshot ->
            userList.clear()
            for (userSnapshot in snapshot.children) {
                try {
                    val id = userSnapshot.child("id").getValue(Int::class.java) ?: 0
                    val nombre = userSnapshot.child("nombre").getValue(String::class.java) ?: ""
                    val password = userSnapshot.child("password").getValue(String::class.java) ?: ""
                    val carrera = userSnapshot.child("carrera").getValue(String::class.java) ?: ""
                    val cursos = userSnapshot.child("cursos").children.mapNotNull { it.getValue(String::class.java) }
                    val tutorias = userSnapshot.child("tutorias").children.mapNotNull {
                        val dia = it.child("dia").getValue(String::class.java) ?: ""
                        val horario = it.child("horario").getValue(String::class.java) ?: ""
                        val curso = it.child("curso").getValue(String::class.java) ?: ""
                        val tutor = it.child("tutor").getValue(String::class.java) ?: ""
                        Tutoria(dia, horario, curso, tutor)
                    }
                    val telefono = userSnapshot.child("telefono").getValue(String::class.java) ?: ""
                    val email = userSnapshot.child("email").getValue(String::class.java) ?: ""
                    val descripcion = userSnapshot.child("descripcion").getValue(String::class.java) ?: ""
                    val horarios = userSnapshot.child("horarios").getValue(String::class.java) ?: ""
                    val avatarName = userSnapshot.child("avatar").getValue(String::class.java) ?: "cuchututor"

                    val avatarResId = when (avatarName) {
                        "cuchututor" -> R.drawable.cuchututor
                        else -> R.drawable.cuchututor
                    }

                    val usuario = Usuario(
                        id = id,
                        nombre = nombre,
                        password = password,
                        carrera = carrera,
                        cursos = androidx.compose.runtime.snapshots.SnapshotStateList<String>().apply { addAll(cursos) },
                        tutorias = androidx.compose.runtime.snapshots.SnapshotStateList<Tutoria>().apply { addAll(tutorias) },
                        telefono = telefono,
                        email = email,
                        descripcion = descripcion,
                        horarios = horarios,
                        avatar = avatarResId
                    )
                    userList.add(usuario)
                } catch (e: Exception) {
                    Log.e("Firebase", "Error parseando usuario: ${e.message}")
                }
            }
            Log.d("Firebase", "Usuarios cargados: ${userList.size}")
            onComplete?.invoke()
        }.addOnFailureListener { e ->
            Log.e("Firebase", "Error al obtener usuarios: ${e.message}")
            onComplete?.invoke()
        }
    }

    fun checkLogin(email: String, password: String): Boolean {
        return userList.any { it.email == email && it.password == password }
    }

    fun login(email: String, password: String) {
        usuarioLogeado = userList.find { it.email == email && it.password == password }
        Log.d("Login", "Usuario logueado: ${usuarioLogeado?.nombre}")
    }

    fun logout() {
        usuarioLogeado = null
    }


    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()


    fun cargarTutorias() {

        viewModelScope.launch {

            _homeUiState.value = HomeUiState(isLoading = true)
            try {
                val usuario = usuarioLogeado

                if (usuario == null) {
                    _homeUiState.value = HomeUiState(error = "Usuario no logueado")
                    return@launch
                }

                // Obtener las tutorías más recientes desde Firebase
                val ref = db.getReference("usuarios/${usuario.id}/tutorias")
                ref.get().addOnSuccessListener { snapshot ->
                    val nuevasTutorias = snapshot.children.mapNotNull {
                        val dia = it.child("dia").getValue(String::class.java) ?: return@mapNotNull null
                        val horario = it.child("horario").getValue(String::class.java) ?: return@mapNotNull null
                        val curso = it.child("curso").getValue(String::class.java) ?: return@mapNotNull null
                        val tutor = it.child("tutor").getValue(String::class.java) ?: return@mapNotNull null
                        Tutoria(dia, horario, curso, tutor)
                    }

                    usuario.tutorias.clear()
                    usuario.tutorias.addAll(nuevasTutorias)
                    _homeUiState.value = HomeUiState(isLoading = false, tutorias = nuevasTutorias)
                }.addOnFailureListener {
                    _homeUiState.value = HomeUiState(error = "Error al leer tutorías")
                }

            } catch (e: Exception) {
                _homeUiState.value = HomeUiState(isLoading = false, error = e.message)
                }
        }
    }

    fun agregarTutoria(nueva: Tutoria) {
        viewModelScope.launch {
            val usuario = usuarioLogeado ?: return@launch
            try {
                usuario.tutorias.add(nueva)

                // Actualizar en Firebase
                val ref = db.getReference("usuarios/${usuario.id}/tutorias")
                ref.setValue(usuario.tutorias.toList())
                    .addOnSuccessListener {
                        Log.d("Firebase", "Tutoría agregada correctamente.")
                    }
                    .addOnFailureListener {
                        Log.e("Firebase", "Error al agregar tutoría: ${it.message}")
                    }

                // Actualizar UI localmente
                _homeUiState.value = _homeUiState.value.copy(
                    tutorias = usuario.tutorias.toList()
                )
            } catch (e: Exception) {
                Log.e("Firebase", "Error en agregarTutoria: ${e.message}")
                }
            }
    }
    fun eliminarTutoria(tutoria: Tutoria) {
        viewModelScope.launch {
            val usuario = usuarioLogeado ?: return@launch
            try {
                usuario.tutorias.remove(tutoria)

                // Actualizar en Firebase
                val ref = db.getReference("usuarios/${usuario.id}/tutorias")
                ref.setValue(usuario.tutorias.toList())
                    .addOnSuccessListener {
                        Log.d("Firebase", "Tutoría eliminada correctamente.")
                    }
                    .addOnFailureListener {
                        Log.e("Firebase", "Error al eliminar tutoría: ${it.message}")
                    }

                // Actualizar UI localmente
                _homeUiState.value = _homeUiState.value.copy(
                    tutorias = usuario.tutorias.toList()
                )
            } catch (e: Exception) {
                Log.e("Firebase", "Error en eliminarTutoria: ${e.message}")
                }
            }
    }

    fun updateUsuarioLogeado(
        nombre: String,
        password: String,
        carrera: String,
        cursos: List<String>,
        telefono: String,
        email: String,
        descripcion: String,
        horarios: String
    ) {
        val usuario = usuarioLogeado ?: return

        usuario.nombre = nombre
        usuario.password = password
        usuario.carrera = carrera
        usuario.cursos.clear()
        usuario.cursos.addAll(cursos)
        usuario.telefono = telefono
        usuario.email = email
        usuario.descripcion = descripcion
        usuario.horarios = horarios

        // Actualizar en Firebase
        val ref = db.getReference("usuarios/${usuario.id}")
        val userMap = mapOf(
            "id" to usuario.id,
            "nombre" to usuario.nombre,
            "password" to usuario.password,
            "carrera" to usuario.carrera,
            "cursos" to usuario.cursos.toList(),
            "tutorias" to usuario.tutorias.toList(),
            "telefono" to usuario.telefono,
            "email" to usuario.email,
            "descripcion" to usuario.descripcion,
            "horarios" to usuario.horarios,
            "avatar" to "cuchututor"
        )

        ref.updateChildren(userMap)
            .addOnSuccessListener {
                Log.d("Firebase", "Perfil actualizado correctamente en la base de datos.")
            }
            .addOnFailureListener {
                Log.e("Firebase", "Error al actualizar perfil: ${it.message}")
            }
    }

    fun registrarUsuario(usuario: Usuario, onComplete: (() -> Unit)? = null) {
        try {
            val ref = db.getReference("usuarios/${usuario.id}")
            val userMap = mapOf(
                "id" to usuario.id,
                "nombre" to usuario.nombre,
                "password" to usuario.password,
                "carrera" to usuario.carrera,
                "cursos" to usuario.cursos.toList(),
                "tutorias" to usuario.tutorias.toList(),
                "telefono" to usuario.telefono,
                "email" to usuario.email,
                "descripcion" to usuario.descripcion,
                "horarios" to usuario.horarios,
                "avatar" to "cuchututor"
            )

            ref.setValue(userMap)
                .addOnSuccessListener {
                    Log.d("Firebase", "Usuario creado correctamente en la base de datos.")
                    // Agregar a la lista local también
                    userList.add(usuario)
                    onComplete?.invoke()
                }
                .addOnFailureListener {
                    Log.e("Firebase", "Error al registrar usuario: ${it.message}")
                }

        } catch (e: Exception) {
            Log.e("Firebase", "Error en registrarUsuario: ${e.message}")
        }
    }


}