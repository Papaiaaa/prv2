package com.example.prv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.prv2.ui.theme.PrvTheme


class ActivityAbout : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrvTheme {
                DisplayTextAbout()
            }
        }
    }
}

@Composable
fun DisplayTextAbout() {
    Column {
        Text(text = "About me")
    }
}