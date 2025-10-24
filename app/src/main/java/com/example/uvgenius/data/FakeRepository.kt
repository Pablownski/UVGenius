package com.example.uvgenius.data

import com.example.uvgenius.model.Tutoria
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class FakeRepository {

    fun getTutorias(): Flow<Result<List<Tutoria>>> = flow {
        delay(2000) // Simula red

        val shouldFail = Random.nextBoolean() // Simular éxito o error

        if (shouldFail) {
            emit(Result.failure(Exception("Error al obtener tutorías desde el servidor.")))
        } else {
            val listaFake = listOf(
                Tutoria("Lunes", "10:00 AM", "Programación", "Santiago Cordero"),
                Tutoria("Viernes", "6:00 PM", "Cálculo", "Diego Gudiel")
            )
            emit(Result.success(listaFake))
        }
    }
}
