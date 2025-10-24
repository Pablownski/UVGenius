package com.example.uvgenius.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.uvgenius.ui.view.FakeTutoriaUIState
import com.example.uvgenius.ui.view.FakeTutoriaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FakeScreen(
    navController: NavController,
    fakeTutoriaViewModel: FakeTutoriaViewModel = viewModel()
) {
    val state by fakeTutoriaViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        fakeTutoriaViewModel.cargarTutorias()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Prueba Asincronía") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is FakeTutoriaUIState.Loading -> {
                    CircularProgressIndicator()
                }

                is FakeTutoriaUIState.Success -> {
                    val lista = (state as FakeTutoriaUIState.Success).data
                    Column {
                        Text(text = "Tutorías cargadas correctamente:")
                        lista.forEach {
                            Text(text = "- ${it.curso} con ${it.tutor} el ${it.dia} a las ${it.horario}")
                        }
                    }
                }

                is FakeTutoriaUIState.Error -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = (state as FakeTutoriaUIState.Error).message,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { fakeTutoriaViewModel.cargarTutorias() }) {
                            Text(text= "Reintentar", color = MaterialTheme.colorScheme.tertiary)
                        }
                    }
                }
            }
        }
    }
}
