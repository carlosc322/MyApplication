package com.example.myapplication.Component


import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.myapplication.Firebase.SensorData
import com.google.firebase.Firebase
import com.google.firebase.database.database

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



@Composable
fun HistorialSensor() {

    // lista donde se guardan los datos del sensor
    var listaDatos by remember { mutableStateOf(listOf<SensorData>()) }
    var cargando by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .width(325.dp)
            .fillMaxHeight()
            .offset(y = (-310).dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .width(325.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(190.dp)
                    .height(25.dp)
            ) {
                Text(
                    text = "Los 10 últimos registros",
                    modifier = Modifier.padding(
                        start = 13.dp,
                        top = 6.dp
                    ),
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp,
                    fontSize = 14.sp
                )
            }

            Button(
                onClick = {
                    cargando = true

                    // Nos conectamos al nodo SensorData (un solo objeto)
                    val referencia = Firebase.database.getReference("SensorData")

                    referencia.get()
                        .addOnSuccessListener { snapshot ->
                            // Leemos TODO el nodo como un SensorData
                            val dato = snapshot.getValue(SensorData::class.java)

                            // Si existe, armamos una lista con ese único registro
                            listaDatos = listOfNotNull(dato)

                            cargando = false
                        }
                        .addOnFailureListener {
                            // Si falla, dejamos la lista vacía
                            listaDatos = emptyList()
                            cargando = false
                        }
                },
                modifier = Modifier
                    .width(90.dp)
                    .height(40.dp)
                    .offset(x = 33.dp),
                shape = RoundedCornerShape(6.dp),
            ) {
                Text(if (cargando) "..." else "Cargar ")
            }
        }
    }

    for (dato in listaDatos) {
        Row(
            modifier = Modifier
                .height(500.dp)
                .width(325.dp)
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.Black)
                .offset(y = (-214).dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Temperatura: ${dato.raw_value} °C",
                color = Color.White,
                modifier = Modifier.
                padding(start = 17.dp))
            Spacer(modifier = Modifier.width(30.dp))
            Text("${convertirFecha(dato.timestamp)}",
                color = Color.White,
                modifier = Modifier
                    .padding(end = 17.dp))
        }
    }

    /*Column(
        modifier = Modifier
            .width(320.dp)
            .height(500.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(Color.Transparent)
            .padding(top = 100.dp)
            .offset(y = (-125).dp)
    ) {}*/
}
fun convertirFecha(segundos: Long?): String {
    if (segundos == null) return "Sin fecha"
    val millis = segundos * 1000L        // En tu base está en segundos
    val fecha = Date(millis)
    val formato = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return formato.format(fecha)
}

