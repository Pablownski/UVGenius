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
import androidx.navigation.NavController
import com.example.uvgenius.ViewModel.AppVM
import com.example.uvgenius.ui.components.BottomNavBar
import com.example.uvgenius.ui.components.TopAppBar
import com.example.uvgenius.ui.components.TutorCard
import com.example.uvgenius.R



val UserList = listOf(
    Usuario("Juan", "Shampol07#", "Sistemas", listOf("Cálculo 2", "Física 3", "Plataformas Móviles"), emptyList(),"5514-2209", "sal24374@uvg.edu.gt", avatar = R.drawable.cuchututor),
    Usuario ("Diego", "Municipal132", "Sistemas", listOf("Android", "C++ Pthreads"),emptyList() ,"5555-5555", "diego@uvg.edu.gt", avatar = R.drawable.cuchututor),
    Usuario("Santiago", "Corderito132", "Química", listOf("Química"),emptyList() ,"5555-0000", "santi@uvg.edu.gt", avatar = R.drawable.cuchututor),
    Usuario("Samuel", "Samu132", "Biología", listOf("Ciencias de la vida"),emptyList() ,"5555-1111", "samu@uvg.edu.gt", avatar = R.drawable.cuchututor),
    Usuario("Pablo", "Pablownski", "Marketing", listOf("Marketing Digital", "Cálculo para el mercadeo"), emptyList(), "5555-2222", "pablo@uvg.edu.gt", avatar = R.drawable.cuchututor),
    Usuario("Daniel", "Danielson132", "Civil Arquitectonica", listOf("Pisos 1, Pisos Picados 3"),emptyList(),  "5555-3333", "daniel@uvg.edu.gt", avatar = R.drawable.cuchututor),
    Usuario("Juan", "Juanito057", "Bioquimica", listOf("Quimica 2", "FisicoQuimica"),emptyList(), "5555-4444", "juan@uvg.edu.gt", avatar = R.drawable.cuchututor),
  )


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorsListScreen(
    navController: NavController,
    listaTutores: List<Usuario> = UserList,
    viewModel: AppVM,
    modifier: Modifier
) {
    Scaffold(
        topBar = { TopAppBar(
            onLogout = {
                viewModel.usuarioLogeado = null
                navController.navigate("login") { popUpTo("home") { inclusive = true } }
            }) },
        bottomBar = { BottomNavBar(navController, modifier) }
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

