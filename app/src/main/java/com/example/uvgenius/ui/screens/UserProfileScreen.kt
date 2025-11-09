package com.example.uvgenius.ui.screens

import coil.compose.AsyncImage
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.uvgenius.navigation.Routes
import com.example.uvgenius.ui.components.BottomNavBar
import com.example.uvgenius.ui.components.TopNavBar
import com.example.uvgenius.ui.view.AppVM
import com.example.uvgenius.R
import com.example.uvgenius.ui.components.InfoRow
import com.example.uvgenius.ui.components.EditProfileDialog

@Composable
fun UserProfileScreen(
    navController: NavController,
    viewModel: AppVM
){
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

    var showEdit by remember {mutableStateOf(false)}

    Scaffold(
        topBar = { TopNavBar {
            viewModel.logout()
            navController.navigate(Routes.Login.route){
                popUpTo(0)
                launchSingleTop = true
            }
        } },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(16.dp) // padding interno del card
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = user.avatar,
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(180.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(Modifier.height(12.dp))
                    Text(
                        user.nombre,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFE6E3E3), RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            if (user.descripcion.isBlank()) "Sin descripción" else user.descripcion,
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }

                    Spacer(Modifier.height(20.dp))
                    InfoRow(R.drawable.ic_school, listOf(user.carrera))
                    Spacer(Modifier.height(12.dp))
                    InfoRow(
                        R.drawable.ic_book,
                        if (user.cursos.isEmpty())
                            listOf("Proponte como tutor de un curso con el botón inferior!")
                        else user.cursos
                    )
                    Spacer(Modifier.height(12.dp))
                    InfoRow(R.drawable.ic_people, listOf("Contacto: ${user.telefono}"))
                    Spacer(Modifier.height(12.dp))
                    InfoRow(
                        R.drawable.ic_calendar,
                        listOf(
                            if (user.horarios.isBlank()) "Horarios disponibles: —"
                            else "Horarios disponibles: ${user.horarios}"
                        )
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { showEdit = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text("Configurar perfil", color = Color.White, fontSize = 16.sp)
            }
        }
    }

    if (showEdit) {
        EditProfileDialog(
            user = user,
            onDismiss = { showEdit = false },
            onConfirm = { nombre, password, carrera, cursos, telefono, email, descripcion, horarios, avatar ->
                viewModel.updateUsuarioLogeado(
                    nombre = nombre,
                    password = password,
                    carrera = carrera,
                    cursos = cursos,
                    telefono = telefono,
                    email = email,
                    descripcion = descripcion,
                    horarios = horarios,
                    avatar = avatar
                )
                showEdit = false
            }
        )
    }
}