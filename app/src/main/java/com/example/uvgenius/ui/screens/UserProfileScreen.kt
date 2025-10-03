package com.example.uvgenius.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.uvgenius.navigation.Routes
import com.example.uvgenius.ui.components.BottomNavBar
import com.example.uvgenius.ui.components.TopNavBar
import com.example.uvgenius.ui.view.AppVM

@Composable
fun UserProfileScreen(
    navController: NavController,
    viewModel: AppVM
){
    val user = viewModel.usuarioLogeado
    LaunchedEffect(user) {
        if (user == null) {
            navController.navigate(Routes.Login.route) {
                popUpTo(0)
                launchSingleTop = true
            }
        }
    }
    if (user == null) {
        return
    }

    Scaffold(
        topBar = { TopNavBar { viewModel.logout() } },
        bottomBar = { BottomNavBar(navController) }
    ){paddingValues ->
        Text("PLACEHOLDER", modifier = Modifier.padding(paddingValues))

    }
}