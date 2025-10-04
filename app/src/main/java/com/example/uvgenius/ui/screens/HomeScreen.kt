package com.example.uvgenius.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.uvgenius.ui.theme.PrimaryGreen

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
    if (user == null) return

    var showBottomSheet by remember { mutableStateOf(false) }


    var dia by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf("") }
    var curso by remember { mutableStateOf("") }
    var tutor by remember { mutableStateOf( "") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomNavBar(navController) },
        topBar = { TopNavBar(onLogout = { viewModel.logout() }) },
    ) { innerPadding ->
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
                if (userTutorias.isEmpty()) {
                    item {
                        Text(
                            "No tienes tutorías programadas",
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                } else {
                    items(userTutorias) { tutoria ->
                        TutoriaCard(tutoria)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { showBottomSheet = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = "Agregar Tutoría",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }


        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                containerColor = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Nueva Tutoría", fontSize = 20.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)

                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = dia,
                        onValueChange = { dia = it },
                        label = { Text("Día (ej. Lunes)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = horario,
                        onValueChange = { horario = it },
                        label = { Text("Horario (ej. 11:00 - 13:00)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = curso,
                        onValueChange = { curso = it },
                        label = { Text("Curso") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = tutor,
                        onValueChange = { tutor = it },
                        label = { Text("Tutor") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(12.dp))

                    Button(
                        onClick = {
                            if (dia.isNotBlank() && horario.isNotBlank() && curso.isNotBlank()) {
                                val nuevaTutoria = Tutoria(
                                    dia = dia,
                                    horario = horario,
                                    curso = curso,
                                    tutor = tutor
                                )
                                user.tutorias.add(nuevaTutoria)


                                dia = ""
                                horario = ""
                                curso = ""
                                tutor = ""

                                showBottomSheet = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                    ) {
                        Text("Guardar", color = Color.White)
                    }

                    Spacer(Modifier.height(8.dp))

                    TextButton(onClick = { showBottomSheet = false }) {
                        Text("Cancelar", color = Color.Gray)
                    }
                }
            }
        }
    }
}
