package com.example.uvgenius.navigation

sealed class Routes(val route: String) {
    data object Login: Routes(route = "login")
    data object Register: Routes(route = "register")
    data object Home: Routes("home")
    data object TutorDetail: Routes("tutor_detail")
    data object TutorList: Routes("tutor_list")
    data object UserProfile: Routes("user_profile")
}


