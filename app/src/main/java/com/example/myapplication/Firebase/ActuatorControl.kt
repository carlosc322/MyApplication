package com.example.myapplication.Firebase

data class ActuatorControl(
    var enabled: Boolean? = false,
    var intensity: Int = 15,//Intencidad del actudor-> ventilacion
    var minIntensity: Int = 0,
    var maxIntensity: Int = 255,
    var last_update: Int = 0,//fecha del ultimo cambio
    var mode: String = "manual",
    var maximo: Int = 255,//valor maximo permitido

){
    constructor() : this(enabled = false, intensity = 15, minIntensity = 0, maxIntensity = 255, last_update = 0, mode = "manual", maximo = 255)
}