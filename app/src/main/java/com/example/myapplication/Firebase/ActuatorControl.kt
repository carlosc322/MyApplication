package com.example.myapplication.Firebase

data class ActuatorControl(
    var enabled: Boolean? = false,
    var intensity: Int = 0,//Intencidad del actudor-> ventilacion
    var minIntensity: Int = 0,
    var maxIntensity: Int = 0,
    var last_update: Int = 0,//fecha del ultimo cambio
    var mode: String = "manual",
){
    constructor() : this(enabled = false, intensity = 0, minIntensity = 0, maxIntensity = 0, last_update = 0, mode = "off")
}

