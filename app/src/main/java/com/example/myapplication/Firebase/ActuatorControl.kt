package com.example.myapplication.Firebase

data class ActuatorControl(
    var enabled: Boolean = false,
    var intensity: Int = 15,//Intencidad del actudor-> ventilacion
    var minIntensity: Int = 0,
    var maxIntensity: Int = 255,
    var last_update: Int = 0,//fecha del ultimo cambio
    var mode: String = "manual",
    var maximo: Int = 255,//valor maximo permitido

){
    constructor() : this(false, 0, 0, 0,0,"", maximo = 255)
}