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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.uvgenius.ui.view.AppVM
import com.example.uvgenius.model.Tutoria
import com.example.uvgenius.navigation.Routes
import com.example.uvgenius.ui.components.BottomNavBar
import com.example.uvgenius.ui.components.TopNavBar
import com.example.uvgenius.ui.components.TutoriaCard
import com.example.uvgenius.ui.theme.ContentDarkGray
import com.example.uvgenius.ui.theme.PrimaryGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: AppVM) {
    val user = viewModel.usuarioLogeado
    val uiState = viewModel.homeUiState.collectAsStateWithLifecycle().value
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
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var dia by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf("") }
    var curso by remember { mutableStateOf("") }
    var tutor by remember { mutableStateOf("") }

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
            if (uiState.isLoading) {
                CircularProgressIndicator()
                Spacer(Modifier.height(8.dp))
            }
            uiState.error?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(8.dp))
            }

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                if (uiState.tutorias.isEmpty()) {
                    item {
                        Text(
                            "No tienes tutorías programadas",
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                } else {
                    items(uiState.tutorias) { tutoria ->
                        TutoriaCard(tutoria)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {viewModel.cargarTutorias()},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = "Cargar tutorías",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { showBottomSheet = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
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
                sheetState = sheetState,
                containerColor = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Nueva Tutoría", fontSize = 20.sp)

                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = dia,
                        onValueChange = { dia = it },
                        label = { Text("Día (ej. Lunes)") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = ContentDarkGray,
                            focusedLabelColor = PrimaryGreen,
                            cursorColor = PrimaryGreen
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = horario,
                        onValueChange = { horario = it },
                        label = { Text("Horario (ej. 11:00 - 13:00)") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = ContentDarkGray,
                            focusedLabelColor = PrimaryGreen,
                            cursorColor = PrimaryGreen
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = curso,
                        onValueChange = { curso = it },
                        label = { Text("Curso") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = ContentDarkGray,
                            focusedLabelColor = PrimaryGreen,
                            cursorColor = PrimaryGreen
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = tutor,
                        onValueChange = { tutor = it },
                        label = { Text("Tutor") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = ContentDarkGray,
                            focusedLabelColor = PrimaryGreen,
                            cursorColor = PrimaryGreen
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(12.dp))

                    Button(
                        onClick = {
                            if (dia.isNotBlank() && horario.isNotBlank() && curso.isNotBlank()) {
                                val nueva = Tutoria(dia = dia, horario = horario, curso = curso, tutor = tutor)
                                val actuales = uiState.tutorias.toMutableList()
                                actuales.add(nueva)
                                val merged = actuales.toList()
                                showBottomSheet = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                    ) {
                        Text("Guardar", color = Color.White, fontSize = 15.sp)
                    }

                    Spacer(Modifier.height(8.dp))

                    TextButton(
                        onClick = { showBottomSheet = false },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                    ) {
                        Text(text = "Cancelar", color = Color.White, fontSize = 15.sp)
                    }
                }
            }
        }
    }
}
