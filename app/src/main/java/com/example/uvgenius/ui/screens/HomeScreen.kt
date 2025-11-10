package com.example.uvgenius.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.uvgenius.R
import com.example.uvgenius.ui.view.AppVM
import com.example.uvgenius.model.Tutoria
import com.example.uvgenius.navigation.Routes
import com.example.uvgenius.ui.components.BottomNavBar
import com.example.uvgenius.ui.components.TopNavBar
import com.example.uvgenius.ui.components.TutoriaCard
import com.example.uvgenius.ui.theme.ContentDarkGray
import com.example.uvgenius.ui.theme.PrimaryGreen
import androidx.compose.material.icons.filled.CloudOff

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: AppVM) {
    val user = viewModel.usuarioLogeado
    val uiState = viewModel.homeUiState.collectAsStateWithLifecycle().value
    val isOnline = viewModel.isOnline.collectAsStateWithLifecycle().value
    LaunchedEffect(user) {
        if (user == null) {
            navController.navigate(Routes.Login.route) {
                popUpTo(0)
                launchSingleTop = true
            }
        }
    }
    if (user == null) return
    LaunchedEffect(Unit) {
        viewModel.cargarTutorias()
    }

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var dia by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf("") }
    var curso by remember { mutableStateOf("") }
    var tutor by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomNavBar(navController) },
        topBar = {
            Column {
                TopNavBar(onLogout = {
                    viewModel.logout()
                    navController.navigate(Routes.Login.route){
                        popUpTo(0)
                        launchSingleTop = true
                    }
                })
                if (!isOnline) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(0xFFFF9800),
                        tonalElevation = 4.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                painter = painterResource(R.drawable.ic_offline),
                                contentDescription = "Offline",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )


                            Icon(
                                imageVector = Icons.Default.CloudOff,
                                contentDescription = "Offline",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )

                            Spacer(Modifier.width(8.dp))
                            Text(
                                "Modo offline - Los cambios se sincronizarán cuando haya conexión",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        },
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
                            if (uiState.isLoading) "" else "No tienes tutorías programadas",
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                } else {
                    items(uiState.tutorias) { tutoria ->
                        TutoriaCard(
                            tutoria = tutoria,
                            onDelete = { viewModel.eliminarTutoria(tutoria) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))


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
                                if (!uiState.isLoading){
                                    viewModel.agregarTutoria(nueva)
                                }
                                showBottomSheet = false
                                dia = ""
                                horario = ""
                                curso = ""
                                tutor = ""
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
