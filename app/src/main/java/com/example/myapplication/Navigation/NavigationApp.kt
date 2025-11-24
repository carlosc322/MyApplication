package com.example.myapplication.Navigation

// Jetpack Compose runtime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

// 🧱 Layout y estilo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.Screens.FirstApp
import com.example.myapplication.Screens.LoginApp
import com.example.myapplication.Screens.SecondApp
import com.example.myapplication.Screens.ThirdApp

import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun AppNavegation(){ //ESTA FUNCIÓN NO SE MUESTRA
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "login"   //Crear el contenedor de navegación (NavHost)
    ){
        composable("login"){
            LoginApp(navController)
        }
        composable("firstApp"){// --> Si haces navController.navigate("home"), vienes aquí.
            FirstApp(navController)
        }
        composable("secondApp"){
            SecondApp(navController)
        }
        composable("thirdApp"){
            ThirdApp(navController)
        }
    }
}


//NavegacionApp() → crea el navController
//BottomBar(navController) → usa ese navController para navegar
//Si BottomBar no recibiera navController, no podría mover pantallas.

@Composable
fun TopBar(navController: NavHostController) {

    // Esto escucha la pantalla actual
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color.DarkGray)
            .padding(start = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // ------- ESTADO → firstApp -------
        Button(
            onClick = {
                if (currentRoute != "firstApp") {   // evita navegar a la misma ruta
                    navController.navigate("firstApp") {
                        launchSingleTop = true      // no duplica la pantalla en el backstack
                    }
                }
            },
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .padding(end = 2.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (currentRoute == "firstApp")
                    Color(0xFF333333)
                else
                    Color.Black
            ),
            shape = RoundedCornerShape(6.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(text = "Estado")
        }
        // ------- CONFIGURACIÓN → secondApp -------
        Button(
            onClick = {
                if (currentRoute != "secondApp") {
                    navController.navigate("secondApp") {
                        launchSingleTop = true
                    }
                }
            },
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .padding(horizontal = 2.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (currentRoute == "secondApp")
                    Color(0xFF333333)
                else
                    Color.Black
            ),
            shape = RoundedCornerShape(6.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(text = "Configuración")
        }
        // ------- HISTORIAL → thirdApp -------
        Button(
            onClick = {
                if (currentRoute != "thirdApp") {
                    navController.navigate("thirdApp") {
                        launchSingleTop = true
                    }
                }
            },
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .padding(start = 2.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (currentRoute == "thirdApp")
                    Color(0xFF333333)
                else
                    Color.Black
            ),
            shape = RoundedCornerShape(6.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(text = "Historial")
        }
    }
}


@Composable
fun BottomBar(){
    Box(
        modifier = Modifier
            .fillMaxWidth().height(100.dp).background(Color.DarkGray)
    )
}