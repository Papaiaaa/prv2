package com.example.prv2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat.startActivity
import com.example.prv2.ui.theme.PrvTheme
import com.google.firebase.database.*

object DataHolder {
    var mainTitle: String by mutableStateOf("")
}
class ActivityCont : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrvTheme {
                DisplayDataFromFirebase()
            }
        }
    }
}

@Composable
fun DisplayDataFromFirebase() {
    val database = FirebaseDatabase.getInstance("https://popovkotlin-67a40-default-rtdb.firebaseio.com/").reference

    var email by remember { mutableStateOf("") }
    var messenger by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    val valueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                val data = snapshot.value as? Map<*, *>
                data?.let {
                    email = data["email"].toString()
                    messenger = data["messenger"].toString()
                    phone = data["phone"].toString()
                    // Обновляем mainTitle в DataHolder
                    DataHolder.mainTitle = data["mainTitle"].toString()
                }
            }
            // Отключаем слушатель после получения данных
            database.removeEventListener(this)
        }

        override fun onCancelled(error: DatabaseError) {
            // Обработка ошибок
        }
    }

    // Включаем слушатель при первом запуске приложения
    LaunchedEffect(Unit) {
        database.addValueEventListener(valueEventListener)
    }

    Column {
        Text(text = "Email: $email")
        Text(text = "Messenger: $messenger")
        Text(text = "Phone: $phone")
    }
}

