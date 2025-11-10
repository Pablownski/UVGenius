package com.example.uvgenius.data.mappers

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.uvgenius.data.local.TutoriaEntity
import com.example.uvgenius.data.local.UsuarioEntity
import com.example.uvgenius.model.Tutoria
import com.example.uvgenius.model.Usuario
import com.google.gson.Gson

private val gson = Gson()

fun UsuarioEntity.toDomain(tutorias: List<TutoriaEntity>): Usuario {
    val cursosList = gson.fromJson(cursosJson, Array<String>::class.java).toList()
    return Usuario(
        id = id,
        nombre = nombre,
        password = password,
        carrera = carrera,
        cursos = SnapshotStateList<String>().apply { addAll(cursosList) },
        tutorias = SnapshotStateList<Tutoria>().apply {
            addAll(tutorias.map { it.toDomain() })
        },
        telefono = telefono,
        email = email,
        descripcion = descripcion,
        horarios = horarios,
        avatar = avatar
    )
}

fun Usuario.toEntity(): UsuarioEntity {
    return UsuarioEntity(
        id = id,
        nombre = nombre,
        password = password,
        carrera = carrera,
        cursosJson = gson.toJson(cursos.toList()),
        telefono = telefono,
        email = email,
        descripcion = descripcion,
        horarios = horarios,
        avatar = avatar
    )
}

fun TutoriaEntity.toDomain(): Tutoria {
    return Tutoria(
        dia = dia,
        horario = horario,
        curso = curso,
        tutor = tutor
    )
}

fun Usuario.tutoriasToEntities(): List<TutoriaEntity> {
    return tutorias.mapIndexed { index, tutoria ->
        TutoriaEntity(
            usuarioId = id,
            idx = index,
            dia = tutoria.dia,
            horario = tutoria.horario,
            curso = tutoria.curso,
            tutor = tutoria.tutor
        )
    }
}