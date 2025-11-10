package com.example.uvgenius.ui.view

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uvgenius.data.UVRepository
import com.example.uvgenius.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppVM(private val repository: UVRepository) : ViewModel() {

    var userList = mutableStateListOf<Usuario>()
    var usuarioLogeado: Usuario? = null

    val defaultAvatar = "https://firebasestorage.googleapis.com/v0/b/uvgenius-24d2d.firebasestorage.app/o/defaultpfp.png?alt=media&token=6018f011-2b68-49ca-8b1d-7fbccd77de9a"

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    private val _isOnline = MutableStateFlow(true)
    val isOnline = _isOnline.asStateFlow()

    init {
        // Observe Room database for real-time updates
        viewModelScope.launch {
            repository.getUsuarios().collect { usuarios ->
                userList.clear()
                userList.addAll(usuarios)
                Log.d("AppVM", "Usuarios actualizados: ${usuarios.size} (${if (_isOnline.value) "online" else "offline"})")
            }
        }
    }

    fun cargarUsuarios(onComplete: (() -> Unit)? = null) {
        viewModelScope.launch {
            try {
                // Intenta sincronizar desde Firebase
                val syncSuccess = repository.syncFromRemote()
                _isOnline.value = syncSuccess

                if (syncSuccess) {
                    Log.d("AppVM", "✓ Sincronización online completada")
                } else {
                    Log.d("AppVM", "⚠ Modo offline - usando caché local")
                }

                onComplete?.invoke()
            } catch (e: Exception) {
                _isOnline.value = false
                Log.e("AppVM", "Error en sincronización: ${e.message}")
                onComplete?.invoke()
            }
        }
    }

    fun checkLogin(email: String, password: String): Boolean {
        return userList.any { it.email == email && it.password == password }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            usuarioLogeado = repository.login(email, password)
            Log.d("Login", "Usuario logueado: ${usuarioLogeado?.nombre}")
        }
    }

    fun logout() {
        usuarioLogeado = null
    }

    fun cargarTutorias() {
        viewModelScope.launch {
            _homeUiState.value = HomeUiState(isLoading = true)
            try {
                val usuario = usuarioLogeado
                if (usuario == null) {
                    _homeUiState.value = HomeUiState(error = "Usuario no logueado")
                    return@launch
                }

                // Datos ya están en Room, solo los mostramos
                _homeUiState.value = HomeUiState(
                    isLoading = false,
                    tutorias = usuario.tutorias.toList()
                )
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

                // Guarda en Room Y Firebase (si hay conexión)
                repository.upsertUsuario(usuario)

                // Update UI
                _homeUiState.value = _homeUiState.value.copy(
                    tutorias = usuario.tutorias.toList()
                )
                Log.d("AppVM", "Tutoría agregada correctamente")
            } catch (e: Exception) {
                Log.e("AppVM", "Error en agregarTutoria: ${e.message}")
            }
        }
    }

    fun eliminarTutoria(tutoria: Tutoria) {
        viewModelScope.launch {
            val usuario = usuarioLogeado ?: return@launch
            try {
                usuario.tutorias.remove(tutoria)

                // Guarda en Room Y Firebase (si hay conexión)
                repository.upsertUsuario(usuario)

                // Update UI
                _homeUiState.value = _homeUiState.value.copy(
                    tutorias = usuario.tutorias.toList()
                )
                Log.d("AppVM", "Tutoría eliminada correctamente")
            } catch (e: Exception) {
                Log.e("AppVM", "Error en eliminarTutoria: ${e.message}")
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
        horarios: String,
        avatar: String
    ) {
        viewModelScope.launch {
            val usuario = usuarioLogeado ?: return@launch

            usuario.nombre = nombre
            usuario.password = password
            usuario.carrera = carrera
            usuario.cursos.clear()
            usuario.cursos.addAll(cursos)
            usuario.telefono = telefono
            usuario.email = email
            usuario.descripcion = descripcion
            usuario.horarios = horarios
            usuario.avatar = avatar

            // Guarda en Room Y Firebase (si hay conexión)
            repository.upsertUsuario(usuario)
            Log.d("AppVM", "Perfil actualizado correctamente")
        }
    }

    fun registrarUsuario(usuario: Usuario, onComplete: (() -> Unit)? = null) {
        viewModelScope.launch {
            try {
                // Guarda en Room Y Firebase (si hay conexión)
                repository.upsertUsuario(usuario)
                userList.add(usuario)
                Log.d("AppVM", "Usuario registrado correctamente")
                onComplete?.invoke()
            } catch (e: Exception) {
                Log.e("AppVM", "Error al registrar usuario: ${e.message}")
            }
        }
    }
}