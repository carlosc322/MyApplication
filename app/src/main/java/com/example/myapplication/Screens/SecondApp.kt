package com.example.myapplication.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.myapplication.Component.ModoAutomatico
import com.example.myapplication.Component.ModoManual
import com.example.myapplication.Component.MostrarTemp
import com.example.myapplication.Navigation.BottomBar
import com.example.myapplication.Navigation.TopBar


@Composable
fun SecondApp(navController: NavHostController) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
            .statusBarsPadding(),
        topBar = { TopBar(navController) },
        bottomBar = { BottomBar() }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            // Caja gris central
            Box(
                modifier = Modifier
                    .width(390.dp)             // ancho fijo
                    .fillMaxHeight()           // alto completo
                    .background(Color.LightGray)
            ) {
                // Aquí ordenamos TODO en columna
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 16.dp)
                        .verticalScroll(rememberScrollState()), // por si no cabe todo en la pantalla
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp) // separación entre cada bloque
                ) {
                    MostrarTemp()      //  Temperatura
                    ModoAutomatico()   //  Configuración automática
                    ModoManual()       //  Configuración manual
                }
            }
        }
    }
}
