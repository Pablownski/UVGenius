package com.example.uvgenius.navigation

sealed class Routes(val route: String) {
    data object Login: Routes("login")
    data object Register: Routes("register")
    data object Home: Routes("home")
    data object TutorDetail: Routes("tutor_detail") {
        fun createRoute(id: Int) = "tutor_detail/$id"
    }

    data object TutorList: Routes("tutor_list")
    data object UserProfile: Routes("user_profile")
}


