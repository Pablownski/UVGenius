package com.example.uvgenius.data.local

import androidx.room.*

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey val id: Int,
    val nombre: String,
    val password: String,
    val carrera: String,
    val cursosJson: String,
    val telefono: String,
    val email: String,
    val descripcion: String,
    val horarios: String,
    val avatar: String
)

@Entity(
    tableName = "tutorias",
    primaryKeys = ["usuarioId", "idx"]
)
data class TutoriaEntity(
    val usuarioId: Int,
    val idx: Int,
    val dia: String,
    val horario: String,
    val curso: String,
    val tutor: String
)

data class UsuarioWithTutorias(
    @Embedded val usuario: UsuarioEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "usuarioId",
    )
    val tutorias: List<TutoriaEntity>
)
