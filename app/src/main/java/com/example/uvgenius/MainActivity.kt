package com.example.uvgenius

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.uvgenius.ViewModel.AppVM
import com.example.uvgenius.navigation.AppNavHost
import com.example.uvgenius.ui.screens.TutorsListScreen
import com.example.uvgenius.ui.theme.UVGeniusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val viewModel = AppVM()
            UVGeniusTheme {
                AppNavHost(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}