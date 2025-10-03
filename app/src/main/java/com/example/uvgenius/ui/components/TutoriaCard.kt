package com.example.uvgenius.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uvgenius.model.Tutoria


@Composable
fun TutoriaCard(tutoria: Tutoria) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFEEEEEE), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "${tutoria.dia}:",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${tutoria.horario} ${tutoria.curso} con ${tutoria.tutor}",
                fontSize = 16.sp
            )
        }
    }
}