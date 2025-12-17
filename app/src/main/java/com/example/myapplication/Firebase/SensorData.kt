package com.example.myapplication.Firebase


//EL SENSOR DATA TIENE QUE LEER_ESTO
data class SensorData(
    var device: String = "", //creo que era nombre del dispositivo
    var raw_value: Int = 0,//valor crudo del sensor
    var supera_max: Boolean = false,//para saber si paso el limite
    var temp: Int = 0,// la temperatura
    var timestamp: Long = 0,//el tiempo en que se registro la medicion
    var voltage: Double = 0.0,//voltaje
    var wifi_rssi: Int = 0,//nombre del wifi

) {
    // Constructor_BASIO_OBLIGATORIO para Firebase
    constructor() : this("", 0, false,0, 0, 0.0,0)
}