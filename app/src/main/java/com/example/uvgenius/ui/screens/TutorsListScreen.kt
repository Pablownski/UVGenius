package com.example.uvgenius.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uvgenius.model.Usuario
import androidx.compose.ui.tooling.preview.Preview




val UserList = listOf(
    Usuario("Juan", "Shampol07#", "Sistemas", listOf("Cálculo 2", "Física 3", "Plataformas Móviles"), "5514-2209", "sal24374@uvg.edu.gt", "R.drawable.cuchututor"),
    Usuario ("Diego", "Municipal132", "Sistemas", listOf("Android", "C++ Pthreads"), "5555-5555", "diego@uvg.edu.gt", "R.drawable.cuchututor"),
    Usuario("Santiago", "Corderito132", "Química", listOf("Química"), "5555-0000", "santi@uvg.edu.gt", "R.drawable.cuchututor"),
    Usuario("Samuel", "Samu132", "Biología", listOf("Ciencias de la vida"), "5555-1111", "samu@uvg.edu.gt", "R.drawable.cuchututor"),
    Usuario("Pablo", "Pablownski", "Marketing", listOf("Marketing Digital", "Cálculo para el mercadeo"), "5555-2222", "pablo@uvg.edu.gt", "R.drawable.cuchututor"),
    Usuario("Daniel", "Danielson132", "Civil Arquitectonica", listOf("Pisos 1, Pisos Picados 3"), "5555-3333", "daniel@uvg.edu.gt", "R.drawable.cuchututor"),
    Usuario("Juan", "Juanito057", "Bioquimica", listOf("Quimica 2", "FisicoQuimica"), "5555-4444", "juan@uvg.edu.gt", "R.drawable.cuchututor"),
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
fun TutorCard(tutor: Usuario) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

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
            Text(text = Usuario.nombre, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = Usuario.cursos, fontSize = 14.sp)
        }
    }
}


