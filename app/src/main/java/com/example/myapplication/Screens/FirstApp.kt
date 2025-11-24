package com.example.myapplication.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.navigation.NavHostController
import com.example.myapplication.Component.Botones
import com.example.myapplication.Navigation.BottomBar
import com.example.myapplication.Navigation.TopBar


@Composable
fun FirstApp(navController: NavHostController){

    /*
El Scaffold decide su tamaño respecto al padre (la pantalla).
El Box decide su tamaño respecto al Scaffold.*/

    Scaffold(
        modifier = Modifier.fillMaxSize()
            .statusBarsPadding(),
        topBar = {TopBar(navController) },
        bottomBar = { BottomBar() }
    )
    { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()//OCUPA TODO EL ANCHO
                .padding(paddingValues),
            ) {
            Box(
                modifier = Modifier
                    .width(390.dp)//widthFijo
                    .fillMaxHeight()
                    .background(Color.LightGray)
                    .align(Alignment.Center),//para centrar -> width fijo
                contentAlignment = Alignment.Center
            ){
                Botones()
            }
            }
    }

}
