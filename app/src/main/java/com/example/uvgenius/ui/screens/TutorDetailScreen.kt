package com.example.uvgenius.ui.screens

import com.example.uvgenius.model.Usuario



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.uvgenius.R
import com.example.uvgenius.navigation.Routes
import com.example.uvgenius.ui.components.BottomNavBar
import com.example.uvgenius.ui.components.TopNavBar
import com.example.uvgenius.ui.view.AppVM


@Composable
fun TutorDetailScreen(usuario: Usuario, navController: NavController, viewModel: AppVM) {
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
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopNavBar {
                viewModel.logout()
            }
        },
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) {innerPadding ->
        Modifier.padding(innerPadding)
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Imagen de fondo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fondouvg),
                    contentDescription = "Fondo UVG",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )


                Image(
                    painter = painterResource(id = R.drawable.cuchututor),
                    contentDescription = "Foto de tutor",
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .align(Alignment.BottomCenter)
                        .offset(y = 70.dp)
                )
            }

            Spacer(modifier = Modifier.height(80.dp))


            Text(
                text = usuario.nombre,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(60.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            ) {
                Text(text = "Descripción:", fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Información del tutor
            Column(modifier = Modifier.padding(start = 24.dp, end = 16.dp)) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_school),
                        contentDescription = "Carrera",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = usuario.carrera, fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.Top) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_book),
                        contentDescription = "Cursos",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        usuario.cursos.forEach { curso ->
                            Text(text = curso, fontSize = 16.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_people),
                        contentDescription = "Contacto",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Contacto: ${usuario.telefono}", fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = "Horario",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Horarios Disponibles: 11:00 - 17:00", fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

