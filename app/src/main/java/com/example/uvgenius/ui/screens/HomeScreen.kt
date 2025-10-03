package com.example.uvgenius.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uvgenius.model.Tutoria
import com.example.uvgenius.ui.components.TutoriaCard

@Composable
fun HomeScreen(
    tutorias: List<Tutoria>,
    onAgregarTutoriaClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Lista de tutorías
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(tutorias) { tutoria ->
                    TutoriaCard(tutoria)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón para agregar tutoría
            Button(
                onClick = onAgregarTutoriaClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1B5E20) // verde oscuro
                ),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = "Agregar Tutoría",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}