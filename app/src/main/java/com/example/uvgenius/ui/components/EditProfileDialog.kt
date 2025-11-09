package com.example.uvgenius.ui.components

import android.R.attr.label
import android.R.attr.maxHeight
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.uvgenius.model.Usuario
import com.example.uvgenius.ui.theme.ContentDarkGray
import com.example.uvgenius.ui.theme.PrimaryGreen
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileDialog(
    user: Usuario,
    onDismiss: () -> Unit,
    onConfirm: (
        nombre: String,
        password: String,
        carrera: String,
        cursos: List<String>,
        telefono: String,
        email: String,
        descripcion: String,
        horarios: String,
        avatar: String
    ) -> Unit
) {
    var nombre by remember { mutableStateOf(user.nombre) }
    var password by remember { mutableStateOf(user.password) }
    var showPassword by remember { mutableStateOf(false) }
    var carrera by remember { mutableStateOf(user.carrera) }
    var telefono by remember { mutableStateOf(user.telefono) }
    var email by remember { mutableStateOf(user.email) }
    var descripcion by remember { mutableStateOf(user.descripcion) }
    var horarios by remember { mutableStateOf(user.horarios) }

    var newCourse by remember { mutableStateOf("") }
    var cursos by remember { mutableStateOf(user.cursos.toList()) } // edit buffer

    val scroll = rememberScrollState()
    val maxHeight = (LocalWindowInfo.current.containerSize.height * 0.8).dp

    var uploadedImageUrl by remember { mutableStateOf<String?>(null) }
    var isUploading by remember { mutableStateOf(false) }
    var showUploadResult by remember { mutableStateOf(false) }
    var uploadSuccess by remember { mutableStateOf(false) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                Log.d("PhotoPicker", "URI seleccionada: $uri")
                // El usuario seleccionó una imagen, la 'uri' no es nula
                // Iniciar la subida
                isUploading = true
                uploadImageToFirebase(
                    uri = uri,
                    onSuccess = { downloadUrl ->
                        uploadedImageUrl = downloadUrl
                        isUploading = false
                        uploadSuccess = true
                        showUploadResult = true
                        Log.d("FirebaseUpload", "Éxito: $downloadUrl")
                    },
                    onFailure = { exception ->
                        isUploading = false
                        uploadSuccess = false
                        showUploadResult = true
                        Log.e("FirebaseUpload", "Error: ", exception)
                    }
                )
            } else {
                Log.d("PhotoPicker", "No se seleccionó ninguna imagen.")
            }
        }
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(
                        nombre, password, carrera, cursos,
                        telefono, email, descripcion, horarios, uploadedImageUrl ?: user.avatar
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1B5E20),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) { Text("Confirmar") }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1B5E20),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) { Text("Cancelar") }
        },
        title = { Text("Editar perfil") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = maxHeight)
                    .verticalScroll(scroll),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Button(
                    onClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    enabled = !isUploading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1B5E20),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    if (isUploading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text("Subiendo...")
                    } else {
                        Text("Subir avatar")
                    }
                }


                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryGreen,
                        unfocusedBorderColor = ContentDarkGray,
                        focusedLabelColor = PrimaryGreen,
                        cursorColor = PrimaryGreen
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryGreen,
                        unfocusedBorderColor = ContentDarkGray,
                        focusedLabelColor = PrimaryGreen,
                        cursorColor = PrimaryGreen
                    ),
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                imageVector = if (showPassword) Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff,
                                contentDescription = if (showPassword) "Ocultar" else "Mostrar",
                                tint = Color.Black
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = carrera,
                    onValueChange = { carrera = it },
                    label = { Text("Carrera") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryGreen,
                        unfocusedBorderColor = ContentDarkGray,
                        focusedLabelColor = PrimaryGreen,
                        cursorColor = PrimaryGreen
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Text("Cursos como tutor:")
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    cursos.forEach { c ->
                        AssistChip(
                            onClick = {},
                            label = { Text(c) },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Eliminar",
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clickable { cursos = cursos - c },
                                    tint = Color.Black
                                )
                            }
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = newCourse,
                        onValueChange = { newCourse = it },
                        label = { Text("Nuevo curso") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = ContentDarkGray,
                            focusedLabelColor = PrimaryGreen,
                            cursorColor = PrimaryGreen
                        ),
                        modifier = Modifier.weight(1f)
                    )
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

                OutlinedTextField(
                    value = telefono,
                    onValueChange = { telefono = it },
                    label = { Text("Teléfono") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryGreen,
                        unfocusedBorderColor = ContentDarkGray,
                        focusedLabelColor = PrimaryGreen,
                        cursorColor = PrimaryGreen
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryGreen,
                        unfocusedBorderColor = ContentDarkGray,
                        focusedLabelColor = PrimaryGreen,
                        cursorColor = PrimaryGreen
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = descripcion,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryGreen,
                        unfocusedBorderColor = ContentDarkGray,
                        focusedLabelColor = PrimaryGreen,
                        cursorColor = PrimaryGreen
                    ),
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 80.dp)
                )

                OutlinedTextField(
                    value = horarios,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryGreen,
                        unfocusedBorderColor = ContentDarkGray,
                        focusedLabelColor = PrimaryGreen,
                        cursorColor = PrimaryGreen
                    ),
                    onValueChange = { horarios = it },
                    label = { Text("Horarios disponibles (ej. 11:00 - 17:00)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),

                )

                if (showUploadResult) {
                    AlertDialog(
                        onDismissRequest = { showUploadResult = false },
                        confirmButton = {
                            TextButton(onClick = { showUploadResult = false }) {
                                Text("Aceptar", color = PrimaryGreen)
                            }
                        },
                        title = {
                            Text(if (uploadSuccess) "Imagen subida correctamente" else "Error al subir imagen")
                        },
                        text = {
                            if (uploadSuccess) {
                                Text("Tu nueva foto de perfil se ha subido con éxito a Firebase Storage.")
                            } else {
                                Text("Ocurrió un problema al subir la imagen. Intenta nuevamente.")
                            }
                        }
                    )
                }
            }
        }
    )
}

/*
 Esta función fue creada por la AI utilizada por el profesor a manera de explicarnos
 cómo funciona el photopicker.
 */
private fun uploadImageToFirebase(
    uri: Uri,
    onSuccess: (String) -> Unit,
    onFailure: (Exception) -> Unit
) {
    val storage = Firebase.storage

    val fileName = "imagenes/${UUID.randomUUID()}"
    val storageRef = storage.reference.child(fileName)

    storageRef.putFile(uri)
        .addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl
                .addOnSuccessListener { downloadUri ->
                    onSuccess(downloadUri.toString())
                }
                .addOnFailureListener { exception ->
                    onFailure(exception)
                }
        }
        .addOnFailureListener { exception ->
            onFailure(exception)
        }
}