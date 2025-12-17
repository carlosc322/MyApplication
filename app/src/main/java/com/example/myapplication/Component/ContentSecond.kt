package com.example.myapplication.Component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember


import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType


import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.Firebase.LeerFirebase
import com.example.myapplication.Firebase.SensorData
import com.example.myapplication.Firebase.escribirFirebase
import com.example.myapplication.Firebase.ActuatorControl
import com.example.myapplication.Firebase.Configuracion


// Helpers para leer desde Firebase
@Composable
fun DatosActuator(): Triple<ActuatorControl?, Boolean, String?> {
    return LeerFirebase("ActuatorControl", ActuatorControl::class.java)
}
@Composable
fun DatosConf(): Triple<Configuracion?, Boolean, String?> {
    return LeerFirebase("Configuracion",Configuracion::class.java)
}

@Composable
fun DatosSensor(): Triple<SensorData?, Boolean, String?> {
    return LeerFirebase("SensorData", SensorData::class.java)
}

/* -----------------------------------------------------------
   1) MOSTRAR TEMPERATURA
--------------------------------------------------------- */
@Composable
fun MostrarTemp() {
    val (sensor, loading, error) = DatosSensor()

    Box(
        modifier = Modifier
            .width(240.dp)
            .height(120.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color.White)
            .padding(8.dp) // un pequeño padding interno
    ) {
        when {
            loading -> {
                // Mientras carga
                Text(
                    text = "Cargando temperatura...",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
            error != null -> {
                // Si hubo error
                Text(
                    text = "Error al leer: $error",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Yellow,
                    fontSize = 12.sp
                )
            }
            else -> {
                // Cuando ya tenemos el dato del sensor
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Título
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(35.dp)
                            .background(Color.Black),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "Temperatura",
                            modifier = Modifier.padding(horizontal = 10.dp),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }

                    // Valor del sensor en el centro
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${sensor?.temp ?: 0} °C",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 27.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

/* ---------------------------------------------------------
   2) MODO AUTOMÁTICO
--------------------------------------------------------- */
@Composable
fun ModoAutomatico() {

    //  Leer Firebase
    val (configuracion, loadingConf, errorConf) = DatosConf()
    val (actuator, loadingAct, errorAct) = DatosActuator()

    // Estados locales (solo para UI)
    var activo by rememberSaveable { mutableStateOf("no_activo") }
    var mensaje by remember { mutableStateOf("") }

    // Valor REAL desde Firebase
    val conf = configuracion?.conf ?: "no_activo"
    val onoff = actuator?.enabled ?: false

    // SINCRONIZAR UI CON FIREBASE
    LaunchedEffect(conf) {
        activo = conf
    }

    // Color según Firebase
    val colorBoton =
        if (activo == "activo") Color(0xFF00C853)   // VERDE
        else Color.Red                              // ROJO

    val textoBoton =
        if (activo == "activo") "ACTIVO"
        else "NO ACTIVO"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .background(Color.LightGray)
            .offset(y = 6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        // ---------------- TÍTULO ----------------
        Box(
            modifier = Modifier
                .height(33.dp)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topStart = 3.dp,
                        topEnd = 3.dp,
                        bottomEnd = 3.dp
                    )
                )
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Configuración de la ventilación",
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }

        // ---------------- CUADRO AUTOMÁTICO ----------------
        Box(
            modifier = Modifier
                .height(130.dp)
                .fillMaxWidth()
                .offset(y = (-5).dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "MODO AUTOMÁTICO:",
                color = Color.DarkGray,
                modifier = Modifier
                    .padding(bottom = 89.dp, end = 180.dp),
                fontWeight = FontWeight.Bold
            )

            when {
                loadingConf || loadingAct -> {
                    Text("Cargando estado automático...")
                }
                errorConf != null -> {
                    Text("Error Configuración: $errorConf", color = Color.Red)
                }
                errorAct != null -> {
                    Text("Error Actuator: $errorAct", color = Color.Red)
                }
                else -> {

                    Button(
                        onClick = {
                            // (activar / desactivar)
                            val nuevoEstado =
                                if (activo == "activo") "no_activo" else "activo"

                            if (!onoff) {
                                mensaje = "Ventilador apagado"
                            }

                            // Guardar estado automático
                            val nuevaConfig = Configuracion(
                                conf = nuevoEstado
                            )
                            escribirFirebase("Configuracion", nuevaConfig)

                            // 🔹 Enviar actuator SIN cambiar encendido/apagado
                            val valorEnviar = ActuatorControl(
                                enabled = onoff, // SE RESPETA
                                intensity = 0,
                                minIntensity = 180,
                                maxIntensity = 250,
                                mode = "automatico",
                                last_update = (System.currentTimeMillis() / 1000).toInt()
                            )
                            escribirFirebase("ActuatorControl", valorEnviar)
                        },
                        modifier = Modifier
                            .width(170.dp)
                            .height(55.dp)
                            .offset(y = 4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorBoton
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(textoBoton)
                    }
                    if (mensaje.isNotEmpty()) {
                        Text(
                            text = mensaje,
                            color = Color.DarkGray,
                            modifier = Modifier
                                .offset(y = 52.dp)
                                .offset(x = -103.dp),
                            fontSize = 13.sp,
                        )
                    }
                }
            }
        }
    }
}

/* ---------------------------------------------------------
   3) MODO MANUAL
--------------------------------------------------------- */
@Composable
fun ModoManual() {
    val (actuator, loading, error) = DatosActuator()

    if (loading) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(text = "Cargando modo manual...")
        }
        return
    }
    if (error != null) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(text = "Error al leer datos: $error", color = Color.Red)
        }
        return
    }
    // Si llega aquí, podemos usar actuator (aunque igual puede ser null, lo cuidamos)
    val enabledActual = actuator?.enabled ?: false

    // Intensidad inicial sincronizada con Firebase
    var intensidadTexto by rememberSaveable(actuator?.intensity) {
        mutableStateOf(actuator?.intensity?.toString().orEmpty())
    }

    var mensaje by rememberSaveable { mutableStateOf<String?>(null) }

    var mensaje2 by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .offset(y = (-13).dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .height(37.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(
                    topStart = 3.dp,
                    topEnd = 3.dp,
                    bottomEnd = 3.dp
                ))
                .background(Color.Black)
                .padding(10.dp)
                //.offset(y = (-8).dp)
            ,
            contentAlignment = Alignment.Center    // CENTRA EL TEXTO
        ) {
            Text(
                text = "Configuración de la ventilación",
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
        Box(
            modifier = Modifier
                .height(190.dp)
                .fillMaxWidth()
                .offset(y = (-15).dp)
                //.padding(bottom = 29.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color.White),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "MODO MANUAL:",
                color = Color.DarkGray,
                modifier = Modifier
                    .padding(bottom = 150.dp, end = 195.dp),
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(
                value = intensidadTexto,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() } || newValue.isEmpty()) {
                        intensidadTexto = newValue
                    }
                },
                label = { Text("Intensidad (120 - 255)") },
                placeholder = { Text("Ej: 140") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .width(300.dp)
                    .padding(bottom =50.dp)
            )
            if (mensaje != null) {
                Text(
                    text = mensaje!!,
                    color = Color.White
                )
            }
            Button(
                onClick = {
                    //  Limpiar mensajes
                    mensaje = ""
                    mensaje2 = ""

                    val intensidad = intensidadTexto.toIntOrNull()

                    // ️ Validar intensidad
                    if (intensidad == null || intensidad !in 0..255) {
                        mensaje = "Ingresa un valor numérico entre 0 y 255."
                        return@Button
                    }
                    //  Verificar si el ventilador está apagado
                    if (actuator?.enabled == false) {
                        mensaje2 = "Ventilador apagado"
                    }

                    //  Guardar SIEMPRE en Firebase (si pasó la validación)
                    val valorEnviar = ActuatorControl(
                        enabled = enabledActual,
                        intensity = intensidad,
                        minIntensity = 0,
                        maxIntensity = 0,
                        mode = "manual",
                        last_update = (System.currentTimeMillis() / 1000).toInt()
                    )

                    escribirFirebase(
                        field = "ActuatorControl",
                        value = valorEnviar
                    )
                },
                modifier = Modifier
                    .width(100.dp)
                    .height(48.dp)
                    .offset(y = 63.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3)
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "OK")
            }
            // Error de validación (intensidad)
            if (!mensaje.isNullOrEmpty()) {
                Text(text = mensaje!!
                    ,modifier = Modifier
                        .padding(top = 4.dp)
                    .offset(y = 23.dp).offset(x = -35.dp)
                    ,fontSize = 13.sp,)
            }
            // Advertencia ventilador apagado
            if (mensaje2.isNotEmpty()) {
                Text(
                    text = mensaje2,
                    color = Color.DarkGray,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .offset(y = 23.dp).offset(x = -94.dp)
                )
            }
        }
    }
}
