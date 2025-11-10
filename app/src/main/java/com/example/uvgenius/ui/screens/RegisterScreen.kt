package com.example.uvgenius.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.uvgenius.R
import com.example.uvgenius.model.Usuario
import com.example.uvgenius.navigation.Routes
import com.example.uvgenius.ui.theme.BackgroundGreen
import com.example.uvgenius.ui.theme.PrimaryGreen
import com.example.uvgenius.ui.view.AppVM
import kotlin.random.Random
import androidx.compose.runtime.mutableStateListOf
import com.example.uvgenius.ui.theme.ContentDarkGray

@Composable
fun RegisterScreen(navController: NavController, viewModel: AppVM) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var carrera by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    var bottomPadding by remember { mutableStateOf(20.dp) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGreen),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo UVG"
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-40).dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = bottomPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = PrimaryGreen,
                            focusedLabelColor = PrimaryGreen
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Correo electrónico") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = PrimaryGreen,
                            focusedLabelColor = PrimaryGreen
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = PrimaryGreen,
                            focusedLabelColor = PrimaryGreen
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = carrera,
                        onValueChange = { carrera = it },
                        label = { Text("Carrera") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = Color.White,
                            focusedLabelColor = PrimaryGreen
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = telefono,
                        onValueChange = { telefono = it },
                        label = { Text("Teléfono") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = PrimaryGreen,
                            focusedLabelColor = PrimaryGreen
                        )
                    )

                    if (error) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Por favor llena todos los campos",
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        bottomPadding = 5.dp
                    } else {
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    Button(
                        onClick = {
                            if (nombre.isBlank() || email.isBlank() || password.isBlank() || carrera.isBlank() || telefono.isBlank()) {
                                error = true
                            } else {
                                error = false
                                val nuevoUsuario = Usuario(
                                    id = Random.nextInt(10, 999999),
                                    nombre = nombre,
                                    password = password,
                                    carrera = carrera,
                                    cursos = mutableStateListOf(),
                                    tutorias = mutableStateListOf(),
                                    telefono = telefono,
                                    email = email,
                                    descripcion = "Nuevo usuario registrado.",
                                    horarios = "Sin horarios asignados",
                                    avatar = viewModel.defaultAvatar
                                )


                                viewModel.registrarUsuario(nuevoUsuario) {
                                    navController.navigate(Routes.Login.route) {
                                        popUpTo(Routes.Register.route) { inclusive = true }
                                    }
                                }
                            }
                        },

                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryGreen,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Registrar", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.padding(1.dp))
                    Text(
                        text = "¿Ya tienes cuenta? Inicia sesión",
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.clickable {
                            navController.navigate(Routes.Login.route)
                        }
                    )
                }
            }
        }
    }
}