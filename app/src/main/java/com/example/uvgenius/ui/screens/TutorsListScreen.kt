package com.example.uvgenius.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uvgenius.model.Usuario
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.uvgenius.navigation.Routes
import com.example.uvgenius.ui.view.AppVM
import com.example.uvgenius.ui.components.BottomNavBar
import com.example.uvgenius.ui.components.TopNavBar
import com.example.uvgenius.ui.components.TutorCard



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorsListScreen(
    navController: NavController,
    viewModel: AppVM
) {
    val user = viewModel.usuarioLogeado
    LaunchedEffect(user) {
        if (user == null) {
            navController.navigate(Routes.Login.route) {
                popUpTo(0)
                launchSingleTop = true
            }
        }
    }
    if (user == null) {
        return
    }

    Scaffold(
        topBar = { TopNavBar(onLogout = { viewModel.logout() }) },
        bottomBar = { BottomNavBar(navController) }
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

            viewModel.userList.forEach { tutor ->
                if (tutor != viewModel.usuarioLogeado) {
                    TutorCard(tutor)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

