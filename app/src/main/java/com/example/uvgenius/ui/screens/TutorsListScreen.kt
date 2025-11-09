package com.example.uvgenius.ui.screens

import android.R.attr.label
import android.R.attr.singleLine
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.uvgenius.navigation.Routes
import com.example.uvgenius.ui.components.BottomNavBar
import com.example.uvgenius.ui.components.TopNavBar
import com.example.uvgenius.ui.components.TutorCard
import com.example.uvgenius.ui.view.AppVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorsListScreen(
    navController: NavController,
    viewModel: AppVM
) {
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

    var searchQuery by remember { mutableStateOf("") }
    var filterQuery by remember { mutableStateOf("") }
    var showSearchBar by remember { mutableStateOf(false) }
    var showFilterBar by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopNavBar(onLogout = {
            viewModel.logout()
            navController.navigate(Routes.Login.route){
                popUpTo(0)
                launchSingleTop = true
            }
        }) },
        bottomBar = { BottomNavBar(navController) }
    ) { paddingValues ->

        val tutorsFiltrados = viewModel.userList.filter { tutor ->
            val matchNombre = tutor.nombre.contains(searchQuery, ignoreCase = true)
            val matchCurso = if (filterQuery.isNotBlank()) {
                tutor.cursos.any { it.contains(filterQuery, ignoreCase = true) }
            } else true
            matchNombre && matchCurso && tutor != user
        }

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(onClick = {
                        showSearchBar = !showSearchBar
                        if (!showSearchBar) searchQuery = ""
                    }) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color.White)
                    }
                    OutlinedButton(onClick = {
                        showFilterBar = !showFilterBar
                        if (!showFilterBar) filterQuery = ""
                    }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filtro", tint = Color.White)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }


            if (showSearchBar) {
                item {
                    OutlinedTextField(
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            focusedLabelColor = Color.White,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor=Color.White
                        ),
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        label = { Text("Buscar por nombre de tutor", color = Color.White) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }


            if (showFilterBar) {
                item {
                    OutlinedTextField(
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            focusedLabelColor = Color.White,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor=Color.White
                        ),
                        value = filterQuery,
                        onValueChange = { filterQuery = it },
                        label = { Text("Filtrar por curso", color = Color.White) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }


            items(tutorsFiltrados) { tutor ->
                if (tutor.tutorias.isNotEmpty()) {
                    TutorCard(
                        tutor,
                        modifier = Modifier
                            .clickable {
                                navController.navigate(Routes.TutorDetail.createRoute(tutor.id))
                            }
                            .padding(bottom = 8.dp)
                    )
                }
            }
        }
    }
}
