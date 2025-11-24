package com.example.myapplication.Firebase


//EL SENSOR DATA TIENE QUE LEER_ESTO
data class SensorData(
    var device: String = "",
    var raw_value: Int = 0,
    var timestamp: Long = 0,//el tiempo en que se registro la medicion
    var voltage: Double = 0.0,
    var wifi_rssi: Int = 0,
    var supera_max: Boolean = false,//para saber si paso el limite
) {
    // Constructor_BASIO_OBLIGATORIO para Firebase
    constructor() : this("", 0, 0, 0.0, 0, supera_max = false)
}