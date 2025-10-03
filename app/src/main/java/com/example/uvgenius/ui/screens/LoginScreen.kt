package com.example.uvgenius.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(onLogin: (String, String) -> Unit) {
    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.primary
    ){innerPadding ->
        Modifier.padding(innerPadding) // Solo para quitar el error de no usar innerPadding
        Column(
            Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(32.dp))
            Box(Modifier.size(120.dp).background(Color(0xFF1D5E34), CircleShape), contentAlignment = Alignment.Center) {
                Text("UV", color = Color.White, style = MaterialTheme.typography.titleLarge)
            }
            Spacer(Modifier.height(16.dp))

            Text("UVGenios", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = Color(0xFF1D5E34))
            Spacer(Modifier.height(8.dp))
            Text("Bienvenido", style = MaterialTheme.typography.headlineMedium)

            Spacer(Modifier.height(24.dp))
            OutlinedTextField(user, { user = it }, label = { Text("Usuario") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(pass, { pass = it }, label = { Text("Contraseña") }, singleLine = true,
                visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())

            if (error) { Spacer(Modifier.height(8.dp)); Text("Usuario/contraseña inválidos.", color = MaterialTheme.colorScheme.error) }

            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    val ok = user.isNotBlank() && pass.isNotBlank()
                    if (!ok) error = true else onLogin(user, pass)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) { Text("Login") }

            Spacer(Modifier.height(12.dp))
            Text("¿Perdiste tu contraseña?\n¿No tienes cuenta? Regístrate")
        }
    }
}
