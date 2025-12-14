package com.example.myapplication.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.Firebase.escribirFirebase
import com.example.myapplication.Firebase.ActuatorControl
import com.example.myapplication.Firebase.Configuracion


@Composable
fun Botones() {
    // Leer el estado actual del actuador desde Firebase
    val (actuator, loading, error) = DatosActuator()

    // Estado local que se mantiene mientras la app está viva
    var estado by rememberSaveable { mutableStateOf(false) }

    // Cada vez que Firebase traiga un "enabled" nuevo, lo copiamos a estado
    LaunchedEffect(actuator?.enabled) { //<---
        actuator?.enabled?.let { estado = it }
    }

    // Mientras carga, puedes mostrar algo simple
    if (loading) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .background(Color.LightGray),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Cargando estado...", color = Color.Black)
        }
        return
    }

    // Si hay error
    if (error != null) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .background(Color.LightGray),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Error: $error", color = Color.Red)
        }
        return
    }

    // Si llegamos aquí, ya tenemos datos (o al menos estado sincronizado)
    Column(
        modifier = Modifier
            .padding(bottom = 100.dp)
            .background(Color.LightGray)
            .offset(y = (-45).dp),
        verticalArrangement = Arrangement.spacedBy(26.dp)
    ) {

        // ---------- BOTÓN APAGAR ----------
        Button(
            onClick = {
                estado = false

                val valorEnviar = ActuatorControl(
                    enabled = false,
                    intensity = 0, //sólo cuando quieres mantener el valor previamente almacenado en Firebase si existe
                    minIntensity = 0,
                    maxIntensity = 0,
                    mode = "off",
                    last_update = (System.currentTimeMillis() / 1000).toInt()
                )

                escribirFirebase(
                    field = "ActuatorControl",
                    value = valorEnviar
                )

                val valorConfig = Configuracion(
                    conf = "no_activo"
                )

                escribirFirebase("Configuracion", value = valorConfig)
            },
            modifier = Modifier
                .width(150.dp)
                .height(50.dp),
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor =
                    if (!estado) Color.Red else Color(0xFF333333)
            ),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(text = "Apagar")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ---------- BOTÓN ENCENDER ----------
        Button(
            onClick = {
                estado = true

                val valorEnviar = ActuatorControl(
                    enabled = true,//cuando quieres forzar que al presionar el botón la intensidad sea exactamente
                    intensity = actuator?.intensity ?: 15,
                    minIntensity = actuator?.minIntensity ?: 10,
                    maxIntensity = actuator?.maxIntensity ?: 255,
                    mode = actuator?.mode ?: "off",
                    last_update = (System.currentTimeMillis() / 1000).toInt()
                )

                escribirFirebase(
                    field = "ActuatorControl",
                    value = valorEnviar
                )
            },
            modifier = Modifier
                .width(150.dp)
                .height(50.dp),
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor =
                    if (estado) Color(0xFF00C853) else Color(0xFF333333)
            ),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(text = "Encender")
        }
    }
}


