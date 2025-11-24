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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.Firebase.escribirFirebase
import com.example.myapplication.Firebase.ActuatorControl


@Composable
fun Botones(){
    var estado by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(bottom = 100.dp)//Esto sube el colum de top al centro
            .background(Color.LightGray)
            .offset(y = (-45).dp),
        //horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(26.dp)//SEPARA_ENTRE_TODO_LOs_COMPONENTES
    ) {

        // ---------- BOTÓN APAGAR ----------
        Button(
            onClick = {
                estado = false
                var valorEnviar = ActuatorControl(enabled = false)
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
                var valorEnviar = ActuatorControl(enabled = true)
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