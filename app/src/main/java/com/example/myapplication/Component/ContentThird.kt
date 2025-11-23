package com.example.myapplication.Component

// ✔ Jetpack Compose
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.componentestest.Componentes.Firebase.SensorData
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(text = "Los 10 últimos registros")

        Spacer(modifier = Modifier.height(16.dp))
        // 🔘 Botón para recuperar los datos del sensor
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
                    val temporal = mutableListOf<SensorData>() //listaTemporal
                    //  RECUPERAR E ITERAR LOS DATOS
                    for (registroHijo in snapshot.children) {//registro cada Hijo dato registrado
                        val dato = registroHijo.getValue(SensorData::class.java)//combertirlo en objeto
                        if (dato != null) {
                            temporal.add(dato)
                        }
                    }
                    // ORDENAR Y QUEDARSE SOLO CON los 10 ultimos
                    val ordenados = temporal.sortedByDescending { it.timestamp }
                    listaDatos = ordenados.take(10)

                    cargando = false
                }.addOnFailureListener {
                    cargando = false
                }
            }
        ) {
            Text("Cargar historial")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (cargando) {
            Text("Cargando datos")
        }
        // MOSTRAR LOS DATOS EN UN COLUMN CON UN FOR
        for (dato in listaDatos) {
            Row(modifier = Modifier.padding(vertical = 4.dp)) {
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
