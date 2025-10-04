package com.example.uvgenius.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.uvgenius.ui.view.AppVM
import com.example.uvgenius.model.Tutoria
import com.example.uvgenius.navigation.Routes
import com.example.uvgenius.ui.components.BottomNavBar
import com.example.uvgenius.ui.components.TopNavBar
import com.example.uvgenius.ui.components.TutoriaCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: AppVM) {

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
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomNavBar(navController) },
        topBar = { TopNavBar(onLogout = { viewModel.logout() }) },
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                val userTutorias = viewModel.usuarioLogeado!!.tutorias
                if (userTutorias.isEmpty()){
                    item{
                        Text(
                            "Lista aquí las tutorías que hayas agendado con el botón inferior!",
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                } else {
                    items(userTutorias) { tutoria ->
                        TutoriaCard(tutoria)
                    }
                }

            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {},// TODO: Agregar modal bottom sheet
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