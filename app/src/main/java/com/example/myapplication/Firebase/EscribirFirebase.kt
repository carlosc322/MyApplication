package com.example.myapplication.Firebase


import android.util.Log
import com.example.myapplication.Firebase.ActuatorControl
import com.google.firebase.Firebase
import com.google.firebase.database.database

fun escribirFirebase(
    field: String,
    value: ActuatorControl,
    onSuccess: () -> Unit = {},
    onError: (String) -> Unit = {}
) {
    val database = Firebase.database
    val myRef = database.getReference(field)

    Log.d("FirebaseWrite", "Iniciando escritura en: $field")
    Log.d("FirebaseWrite", "Objeto a escribir: $value")

    myRef.setValue(value)
        .addOnSuccessListener {
            Log.d("FirebaseWrite", "✅ Datos escritos exitosamente en $field")
            onSuccess()
        }
        .addOnFailureListener { error ->
            Log.e("FirebaseWrite", "❌ Error escribiendo en $field", error)
            onError("Error: ${error.message}")
        }
}