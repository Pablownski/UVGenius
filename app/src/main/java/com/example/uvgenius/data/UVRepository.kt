package com.example.uvgenius.data

import android.util.Log
import com.example.uvgenius.data.local.UsuarioDao
import com.example.uvgenius.data.mappers.toDomain
import com.example.uvgenius.data.mappers.toEntity
import com.example.uvgenius.data.mappers.tutoriasToEntities
import com.example.uvgenius.model.Tutoria
import com.example.uvgenius.model.Usuario
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class UVRepository(
    private val dao: UsuarioDao,
    private val firebaseRef: DatabaseReference
) {

    fun getUsuarios(): Flow<List<Usuario>> =
        dao.observeUsuarios().map { list ->
            list.map { it.usuario.toDomain(it.tutorias) }
        }

    suspend fun syncFromRemote(): Boolean {
        return try {
            val snapshot = firebaseRef.get().await()
            val usuarios = snapshot.children.mapNotNull { userSnapshot ->
                val id = userSnapshot.child("id").getValue(Int::class.java) ?: return@mapNotNull null
                val nombre = userSnapshot.child("nombre").getValue(String::class.java) ?: ""
                val password = userSnapshot.child("password").getValue(String::class.java) ?: ""
                val carrera = userSnapshot.child("carrera").getValue(String::class.java) ?: ""
                val cursos = userSnapshot.child("cursos").children.mapNotNull { it.getValue(String::class.java) }
                val telefono = userSnapshot.child("telefono").getValue(String::class.java) ?: ""
                val email = userSnapshot.child("email").getValue(String::class.java) ?: ""
                val descripcion = userSnapshot.child("descripcion").getValue(String::class.java) ?: ""
                val horarios = userSnapshot.child("horarios").getValue(String::class.java) ?: ""
                val avatar = userSnapshot.child("avatar").getValue(String::class.java) ?: ""

                val tutorias = userSnapshot.child("tutorias").children.mapNotNull {
                    val dia = it.child("dia").getValue(String::class.java) ?: return@mapNotNull null
                    val horario = it.child("horario").getValue(String::class.java) ?: return@mapNotNull null
                    val curso = it.child("curso").getValue(String::class.java) ?: return@mapNotNull null
                    val tutor = it.child("tutor").getValue(String::class.java) ?: return@mapNotNull null
                    Tutoria(dia, horario, curso, tutor)
                }

                Usuario(
                    id, nombre, password, carrera,
                    androidx.compose.runtime.snapshots.SnapshotStateList<String>().apply { addAll(cursos) },
                    androidx.compose.runtime.snapshots.SnapshotStateList<com.example.uvgenius.model.Tutoria>()
                        .apply { addAll(tutorias) },
                    telefono, email, descripcion, horarios, avatar
                )
            }


            dao.clearUsuarios()
            usuarios.forEach { u ->
                dao.upsertUsuarios(u.toEntity())
                dao.clearTutorias(u.id)
                dao.upsertTutorias(*u.tutoriasToEntities().toTypedArray())
            }

            Log.d("UVRepository", "Sync exitoso: ${usuarios.size} usuarios")
            true // Sync exitoso
        } catch (e: Exception) {
            Log.w("UVRepository", "Sync falló (modo offline con Room): ${e.message}")
            false
        }
    }

    suspend fun login(email: String, password: String): Usuario? =
        dao.findByCredentials(email, password)?.let { it.usuario.toDomain(it.tutorias) }

    suspend fun upsertUsuario(usuario: Usuario) {
        // PRIMERO guardar en Room (siempre funciona)
        dao.upsertUsuarios(usuario.toEntity())
        dao.clearTutorias(usuario.id)
        dao.upsertTutorias(*usuario.tutoriasToEntities().toTypedArray())


        try {
            firebaseRef.child(usuario.id.toString()).setValue(
                mapOf(
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
                    "avatar" to usuario.avatar
                )
            ).await()
            Log.d("UVRepository", "Usuario sincronizado con Firebase")
        } catch (e: Exception) {
            Log.w("UVRepository", "No se pudo sincronizar con Firebase (offline): ${e.message}")
        }
    }
}