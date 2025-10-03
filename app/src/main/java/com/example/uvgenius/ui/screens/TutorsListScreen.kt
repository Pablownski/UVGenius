package com.example.uvgenius.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uvgenius.model.Tutor
import androidx.compose.ui.tooling.preview.Preview


val listaTutores = listOf(
    Tutor("Juan", "Álgebra Lineal"),
    Tutor("Diego", "Cálculo 2"),
    Tutor("Santiago", "Plataformas Móviles"),
    Tutor("Samuel", "Química"),
    Tutor("Pablo", "Ciencias de la vida"),
    Tutor("Daniel", "Cálculo 2")
)


@Composable
fun TutorsListScreen() {
    Scaffold(
        bottomBar = { BottomBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(onClick = { }) {
                    Icon(Icons.Default.Search, contentDescription = "Buscar")
                }
                OutlinedButton(onClick = { }) {
                    Text("Filtro", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            listaTutores.forEach { tutor ->
                TutorCard(tutor)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun TutorCard(tutor: Tutor) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Círculo verde
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color(0xFF2E7D32), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Avatar",
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(text = tutor.nombre, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = tutor.materia, fontSize = 14.sp)
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TutorsListScreenPreview() {
    TutorsListScreen()
}