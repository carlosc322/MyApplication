package com.example.myapplication.Firebase

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember   //  OJO, remember normal
import androidx.compose.runtime.setValue

import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.awaitCancellation

// VERSIÓN PARA LEER OBJETOS COMPLEJOS
@Composable
fun <T> LeerFirebase(
    field: String,
    valueType: Class<T>
): Triple<T?, Boolean, String?> {

    var currentValue by remember { mutableStateOf<T?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val database = Firebase.database
    val myRef = database.getReference(field)

    LaunchedEffect(field) {
        println("hola")

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    println("holaListener")

                    val value = snapshot.getValue(valueType)
                    currentValue = value
                    isLoading = false
                    errorMessage = null
                    Log.d("FirebaseObject", "Object updated: $value")
                } catch (e: Exception) {
                    errorMessage = "Error parsing data: ${e.message}"
                    isLoading = false
                }
            }

            override fun onCancelled(error: DatabaseError) {
                errorMessage = "Error: ${error.message}"
                isLoading = false
                Log.w("FirebaseObject", "Listen failed", error.toException())
            }
        }

        myRef.addValueEventListener(valueEventListener)
        awaitCancellation()
    }

    return Triple(currentValue, isLoading, errorMessage)
}
