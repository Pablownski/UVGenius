package com.example.uvgenius

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.uvgenius.data.UVRepository
import com.example.uvgenius.data.local.DatabaseProvider
import com.example.uvgenius.navigation.AppNavHost
import com.example.uvgenius.ui.theme.UVGeniusTheme
import com.example.uvgenius.ui.view.AppVM
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()


        val database = DatabaseProvider.get(this)
        val firebaseRef = Firebase.database.reference.child("usuarios")
        val repository = UVRepository(database.usuarioDao(), firebaseRef)

        setContent {
            UVGeniusTheme {
                val navController = rememberNavController()
                val viewModel by remember { mutableStateOf(AppVM(repository)) }

                LaunchedEffect(Unit) {

                    viewModel.cargarUsuarios()
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    AppNavHost(
                        navController = navController,
                        viewModel = viewModel,
                        startDestination = "login"
                    )
                }
            }
        }
    }
}