package com.example.myapplication.Component

// ✔ Jetpack Compose
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

// ✔ Fecha y hora
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



@Composable
fun HistorialSensor() {

    // lista donde se guardan los datos del sensor
    var listaDatos by remember { mutableStateOf(listOf<SensorData>()) }//
    var cargando by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .width(325.dp)
            .fillMaxHeight()
            .offset(y = (-310).dp),
        contentAlignment = Alignment.Center
    ){
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
                Text(text = "Los 10 últimos registros",
                    modifier = Modifier.padding(
                        start = 13.dp,
                        top = 6.dp),
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp,
                    fontSize = 14.sp
                )
            }
            Button(
                onClick = {//<-- no es composable
                    cargando = true
                    /**
                     * Esto significa:
                     * Conéctate a la base de datos Firebase
                     * Entra al nodo SensorData
                     * Ahí es donde están guardados tus valores del sensor
                     */
                    val referencia = Firebase.database.getReference("SensorData")

                    referencia.get().addOnSuccessListener { snapshot ->
                        val temporal = mutableListOf<SensorData>()
                        //  RECUPERAR E ITERAR LOS DATOS
                        for (registroHijo in snapshot.children) {//registro cada Hijo dato registrado
                            val valor = registroHijo.getValue(SensorData::class.java)
                            if (valor != null) {
                                // Conversión de raw_value Double → Int
                                val entero = valor.raw_value.toInt()

                                // Reemplazamos el valor
                                valor.raw_value = entero

                                temporal.add(valor)
                            }
                        }
                        // ORDENAR Y QUEDARSE SOLO CON los 10 ultimos
                        val ordenados = temporal.sortedByDescending { it.timestamp }
                        listaDatos = ordenados.take(10)

                        cargando = false
                    }
                },
                modifier = Modifier
                        .width(90.dp)
                        .height(40.dp)
                        .offset(x = 33.dp),
                shape = RoundedCornerShape(6.dp),
            ) {
                Text("Cargar ")
            }
        }
    }
    Column(
        modifier = Modifier
            .width(320.dp)
            .height(500.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(Color.DarkGray)
            .padding(top = 100.dp)
            .offset(y = (-125).dp)
    ) {
        for (dato in listaDatos) {
            Row(
                modifier = Modifier
                    .height(30.dp)
                    .width(305.dp)
                    .padding(vertical = 4.dp)
                    .background(Color.Green)
            )
            {
                Text("Temperatura: ${dato.raw_value} °C")
                Spacer(modifier = Modifier.width(16.dp))
                Text("Fecha: ${convertirFecha(dato.timestamp)}")
            }
        }
    }
}

fun convertirFecha(segundos: Long?): String {
    if (segundos == null) return "Sin fecha"
    val millis = segundos * 1000L        // En tu base está en segundos
    val fecha = Date(millis)
    val formato = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return formato.format(fecha)
}
