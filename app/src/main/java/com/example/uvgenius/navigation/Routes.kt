package com.example.uvgenius.navigation

sealed class Routes(val route: String) {
    data object LOGIN: Routes(route = "login")
    data object HOME: Routes("home")
    data object TutorDetail: Routes("tutor_detail")
    data object TutorList: Routes("tutor_list")

}


