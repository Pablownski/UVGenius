package com.example.uvgenius.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.uvgenius.ui.screens.*
import com.example.uvgenius.ViewModel.AppVM

@Composable
fun RootNav(nav: NavHostController, vm: AppVM) {
    NavHost(navController = nav, startDestination = Routes.LOGIN) {
        composable(Routes.LOGIN) {
            LoginScreen(onLogin = { u, p ->
                if (vm.login(u, p)) {
                    nav.navigate(Routes.HOME) { popUpTo(Routes.LOGIN) { inclusive = true } }
                }
            })
        }
        composable(Routes.HOME ) { HomeScreen(rootNav = nav, vm = vm) }
        composable("${Routes.TutorDetail}/{id}") { backStack ->
            val id = backStack.arguments?.getString("id")
            val tutor = vm.tutores.find { it.id == id }
            if (tutor != null) TutorDetailScreen(tutor = tutor, onConfirm = { nav.popBackStack() })
        }
    }
}
