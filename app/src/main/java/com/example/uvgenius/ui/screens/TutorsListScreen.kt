package com.example.uvgenius.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.uvgenius.model.Usuario
import androidx.navigation.NavController
import com.example.uvgenius.ui.components.BottomNavBar
import com.example.uvgenius.ui.components.TutorCard

// ---- Pantalla principal ----
@Composable
fun TutorsListScreen(
    listaTutores: List<Usuario>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {},
        bottomBar = { BottomNavBar(navController, modifier) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // ---- Barra de búsqueda + botón filtro ----
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

            // ---- Lista de tutores ----
            listaTutores.forEach { tutor ->
                TutorCard(tutor)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}