package com.example.uvgenius.ui.screens

import android.R.attr.onClick
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.uvgenius.model.Usuario
import com.example.uvgenius.navigation.Routes
import com.example.uvgenius.ui.components.BottomNavBar
import com.example.uvgenius.ui.components.TutorCard
import com.example.uvgenius.ui.components.TopNavBar
import com.example.uvgenius.ui.theme.ContentDarkGray
import com.example.uvgenius.ui.theme.PrimaryGreen
import com.example.uvgenius.ui.view.AppVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorsListScreen(navController: NavController, viewModel: AppVM) {
    val usuarios by remember { derivedStateOf { viewModel.userList } }

    var searchText by remember { mutableStateOf("") }
    var showFilters by remember { mutableStateOf(false) }
    var showTutores by remember { mutableStateOf(true) }
    var showNoTutores by remember { mutableStateOf(true) }

    Scaffold(
        topBar = { TopNavBar { viewModel.logout() } },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = { Text("Buscar", color = PrimaryGreen) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .background(Color.White, RoundedCornerShape(10.dp)),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryGreen,
                        unfocusedBorderColor = PrimaryGreen,
                        focusedLabelColor = PrimaryGreen
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                IconButton(
                    onClick = { showFilters = true },
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.White, RoundedCornerShape(8.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = "Filtros",
                        tint = PrimaryGreen
                    )
                }
            }

            if (showFilters) {
                AlertDialog(
                    onDismissRequest = { showFilters = false },
                    confirmButton = {
                        TextButton(onClick = { showFilters = false }) {
                            Text("Aplicar", color = PrimaryGreen)
                        }
                    },
                    title = { Text("Filtros de búsqueda", fontWeight = FontWeight.Bold) },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Mostrar usuarios:", fontWeight = FontWeight.SemiBold)

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = showTutores,
                                    onCheckedChange = { showTutores = it },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = PrimaryGreen,
                                        uncheckedColor = ContentDarkGray,
                                        checkmarkColor = Color.White
                                    )
                                )
                                Text(text = "Tutores",
                                    modifier = Modifier.clickable{showTutores = !showTutores}
                                    )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = showNoTutores,
                                    onCheckedChange = { showNoTutores = it },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = PrimaryGreen,
                                        uncheckedColor = ContentDarkGray,
                                        checkmarkColor = Color.White
                                    )
                                )
                                Text("No tutores",
                                    modifier = Modifier.clickable{showNoTutores = !showNoTutores}
                                )
                            }
                        }
                    }
                )
            }

            val usuariosFiltrados = usuarios.filter { user ->
                if (user.id == viewModel.usuarioLogeado!!.id){
                    return@filter false
                }

                if (searchText.isBlank()) {
                    return@filter true
                }

                val esTutor = user.cursos.isNotEmpty()

                val visiblePorTipo = when {
                    showTutores && showNoTutores -> true
                    showTutores && !showNoTutores -> esTutor
                    !showTutores && showNoTutores -> !esTutor
                    else -> false
                }
                if (!visiblePorTipo) return@filter false

                user.nombre.unaccent().contains(searchText, ignoreCase = true) ||
                    user.cursos.any { it.unaccent().contains(searchText, ignoreCase = true)} ||
                    user.carrera.unaccent().contains(searchText, ignoreCase = true)
            }

            if (usuariosFiltrados.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No se encontraron usuarios con los filtros de búsqueda.",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(usuariosFiltrados) { usuario ->
                        TutorCard(
                            tutor = usuario,
                            modifier = Modifier.clickable {
                                navController.navigate("${Routes.TutorDetail.route}/${usuario.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}

/*
 Función proveída por Gemini para eliminar tildes de string para búsquedas
 */
private fun String.unaccent(): String {
    val regex = "\\p{InCombiningDiacriticalMarks}+".toRegex()
    val temp = java.text.Normalizer.normalize(this, java.text.Normalizer.Form.NFD)
    return regex.replace(temp, "")
}

