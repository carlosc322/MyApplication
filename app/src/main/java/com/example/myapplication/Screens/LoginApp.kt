package com.example.myapplication.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.myapplication.Navigation.BottomBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginApp(navController: NavHostController) {
    var texto by rememberSaveable { mutableStateOf("") }
    var texto2 by rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),

        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.height(90.dp),
                title = {
                    Text(
                        text = "SISTEMA DE VENTILACIÓN",
                        maxLines = 1,
                        letterSpacing = 2.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.SansSerif,
                        modifier = Modifier.padding(top = 32.dp)  // BAJA EL TEXTO-->Sistema de ventilación
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.DarkGray,
                    titleContentColor = Color.White,
                )
            )
        },
        bottomBar = { BottomBar() }
    ) { innerPadding ->

        // Contenido de la pantalla
        Box(                             // FONDO
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.LightGray)
                .offset(y = (-60).dp),
            contentAlignment = Alignment.Center
        ) {

            Box(                         // TARJETA OSCURA
                modifier = Modifier
                    .width(350.dp)
                    .height(420.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.DarkGray)
                    .offset(y = (-30).dp)    // Mueve toda la tarjeta un poco arriba
            ) {

                Column(                  // CONTENIDO DE LA TARJETA
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 35.dp),//LE da 35px de espacio derecho e izquierdo al formulario
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    // 🔹 TITULO ARRIBA
                    Text(
                        text = "Iniciar sesión",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 3.sp,
                        fontSize = 25.sp,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 65.dp)   // SUBE o BAJA el Login--> el numero es alto mas baja
                    )

                    Spacer(Modifier.height(16.dp))   // espacio bajo el título

                    Text(
                        text = "NOMBRE:",
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                    TextField(
                        value = texto,
                        onValueChange = { texto = it },
                        placeholder = { Text("Nombre de usuario") }
                    )

                    Text(
                        text = "CONTRASEÑA:",
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                    TextField(
                        value = texto2,
                        onValueChange = { texto2 = it },
                        placeholder = { Text("Contraseña") },
                        visualTransformation = PasswordVisualTransformation(),   // OCULTA la contraseña
                        singleLine = true                                         // evita saltos, una sola línea
                    )
                    Button(
                        onClick = {navController.navigate("firstApp")},
                        modifier = Modifier
                            .width(100.dp)
                            .height(60.dp)
                            .padding(top = 16.dp)// Más espacio hacia arriba--> aleja el boton ingresar de input
                            .align(Alignment.End),// Mueve el botón a la derecha
                        shape = RoundedCornerShape(8.dp)
                    ){
                        Text("Ingresar")
                    }

                }
            }
        }


    }
}
