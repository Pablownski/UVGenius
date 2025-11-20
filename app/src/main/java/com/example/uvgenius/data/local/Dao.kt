package com.example.uvgenius.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Transaction
    @Query("SELECT * FROM usuarios")
    fun observeUsuarios(): Flow<List<UsuarioWithTutorias>>

    @Transaction
    @Query("SELECT * FROM usuarios WHERE email = :email AND password = :password LIMIT 1")
    suspend fun findByCredentials(email: String, password: String): UsuarioWithTutorias?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUsuarios(vararg usuarios: UsuarioEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTutorias(vararg tutorias: TutoriaEntity)

    @Query("DELETE FROM usuarios")
    suspend fun clearUsuarios()

    @Query("DELETE FROM tutorias WHERE usuarioId = :userId")
    suspend fun clearTutorias(userId: Int)
}
