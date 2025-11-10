package com.example.uvgenius.ui.screens

import android.R.attr.contentDescription
import com.example.uvgenius.model.Usuario



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.uvgenius.R
import com.example.uvgenius.navigation.Routes
import com.example.uvgenius.ui.components.BottomNavBar
import com.example.uvgenius.ui.components.InfoRow
import com.example.uvgenius.ui.components.TopNavBar
import com.example.uvgenius.ui.view.AppVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorDetailScreen(usuario: Usuario, navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Perfil de: ${usuario.nombre}", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2E7D32)
                )
            )
        },
        bottomBar = { BottomNavBar(navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFF388E3C))
        ) {


            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {

                Image(
                    painter = painterResource(id = R.drawable.fondouvg),
                    contentDescription = "Fondo UVG",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(top = 220.dp, start = 16.dp, end = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 80.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = usuario.nombre,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
                                .padding(12.dp)
                        ) {
                            Text(
                                if (usuario.descripcion.isBlank()) "Sin descripción disponible" else usuario.descripcion,
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        InfoRow(R.drawable.ic_school, listOf(usuario.carrera))
                        Spacer(Modifier.height(12.dp))
                        InfoRow(
                            R.drawable.ic_book,
                            if (usuario.cursos.isEmpty()) listOf("No tiene cursos registrados")
                            else usuario.cursos
                        )
                        Spacer(Modifier.height(12.dp))
                        InfoRow(R.drawable.ic_people, listOf("Contacto: ${usuario.telefono}"))
                        Spacer(Modifier.height(12.dp))
                        InfoRow(R.drawable.ic_calendar, listOf("Horarios Disponibles: ${usuario.horarios}"))
                    }
                }
                Row (
                    modifier = Modifier.padding(top = 115.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    AsyncImage(
                        model = usuario.avatar,
                        contentDescription = "Foto de tutor",
                        modifier = Modifier
                            .size(170.dp)
                            .clip(CircleShape)
                            .border(4.dp, Color(0xFF2E7D32), CircleShape)
                            .offset(y = (0).dp),
                        contentScale = ContentScale.Crop
                    )
                }

            }
        }
    }
}

