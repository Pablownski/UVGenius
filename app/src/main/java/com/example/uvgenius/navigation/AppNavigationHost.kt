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

        composable("${Routes.TutorDetail.route}/{id}") { backStack ->
            val id = backStack.arguments?.getString("id")?.toIntOrNull()
            val tutor = viewModel.userList.find { it.id == id }
            if (tutor != null) {
                TutorDetailScreen(tutor, navController, viewModel)
            }
        }

        composable(Routes.TutorList.route) {
            TutorsListScreen(navController, viewModel)
        }
        composable(Routes.UserProfile.route) {
            UserProfileScreen(navController, viewModel)
        }
        composable(Routes.Register.route) {
            RegisterScreen(navController, viewModel)
        }
        composable("fakeTutoria") {
            FakeScreen(navController = navController)
        }

    }
}
