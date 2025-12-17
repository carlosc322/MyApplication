package com.example.myapplication.Firebase

data class ActuatorControl(
    var enabled: Boolean? = false,// ventilador encendido true, apagado false
    var intensity: Int = 0,//Intencidad del actudor-> para modo manual- valor fijo
    var minIntensity: Int = 0,//Para el modo automatico --> Rango min
    var maxIntensity: Int = 0,//Para el modo automatico --> Rango max
    var last_update: Int = 0,//fecha del ultimo cambio
    var mode: String = "manual",//Manual o Automatico o off
){
    constructor() : this(enabled = false, intensity = 0, minIntensity = 0, maxIntensity = 0, last_update = 0, mode = "off")
}

