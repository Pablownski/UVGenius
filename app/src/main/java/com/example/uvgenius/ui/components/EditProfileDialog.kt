package com.example.uvgenius.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.uvgenius.model.Usuario

@Composable
fun EditProfileDialog(
    user: Usuario,
    onDismiss: () -> Unit,
    onConfirm: (
        password: String,
        carrera: String,
        cursos: List<String>,
        telefono: String,
        email: String,
        descripcion: String,
        horarios: String
    ) -> Unit
) {
    var password by remember { mutableStateOf(user.password) }
    var showPassword by remember { mutableStateOf(false) }
    var carrera by remember { mutableStateOf(user.carrera) }
    var telefono by remember { mutableStateOf(user.telefono) }
    var email by remember { mutableStateOf(user.email) }
    var descripcion by remember { mutableStateOf(user.descripcion) }
    var horarios by remember { mutableStateOf(user.horarios) }

    var newCourse by remember { mutableStateOf("") }
    var cursos by remember { mutableStateOf(user.cursos.toList()) } // edit buffer

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = { onConfirm(password, carrera, cursos, telefono, email, descripcion, horarios) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1B5E20),
                    contentColor = Color.White
                )
            ){
                Text("Confirmar")
            }
        },
        dismissButton = { TextButton(
            onClick = onDismiss,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1B5E20),
                contentColor = Color.White
            )
        ) { Text("Cancelar") } },
        title = { Text("Editar perfil") },
        text = {
            Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        // esconder contraseña
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                imageVector = if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (showPassword) "Ocultar" else "Mostrar"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(carrera, { carrera = it }, label = { Text("Carrera") }, singleLine = true, modifier = Modifier.fillMaxWidth())

                Text("Cursos como tutor:")
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    cursos.forEach { c ->
                        AssistChip(
                            onClick = { },
                            label = { Text(c) },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Eliminar",
                                    modifier = Modifier.size(16.dp).clickable { cursos = cursos - c },
                                    tint = Color.Black
                                )
                            }
                        )
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(newCourse, { newCourse = it }, label = { Text("Nuevo curso") }, singleLine = true, modifier = Modifier.weight(1f))
                    Button(
                        onClick = {
                            val t = newCourse.trim()
                            if (t.isNotEmpty() && t !in cursos) cursos = cursos + t
                            newCourse = ""
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1B5E20),
                            contentColor = Color.White
                        )
                    ) { Text("Añadir") }
                }

                OutlinedTextField(telefono, { telefono = it }, label = { Text("Teléfono") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(email, { email = it }, label = { Text("Email") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(descripcion, { descripcion = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth().heightIn(min = 80.dp))
                OutlinedTextField(horarios, { horarios = it }, label = { Text("Horarios disponibles (ej. 11:00 - 17:00)") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            }
        }
    )
}
