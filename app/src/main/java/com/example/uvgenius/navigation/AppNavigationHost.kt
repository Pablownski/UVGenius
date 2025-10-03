package com.example.uvgenius.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.uvgenius.ui.screens.*
import com.example.uvgenius.ui.view.AppVM

@Composable
fun AppNavHost(navController: NavHostController, viewModel: AppVM) {
    NavHost(navController = navController, startDestination = Routes.Login.route) {
        composable(Routes.Login.route) {
            LoginScreen(navController = navController, viewModel = viewModel)
        }
        composable(Routes.Home.route) {
            HomeScreen(navController = navController, viewModel = viewModel)
        }
        /*
        composable("${Routes.TutorDetail}/{id}") { backStack ->
            val id = backStack.arguments?.getString("id")
            val tutor = viewModel.tutores.find { it.id == id }
            if (tutor != null) TutorDetailScreen(tutor = tutor, onConfirm = { navController.popBackStack() })
        }
        */
    }
}
