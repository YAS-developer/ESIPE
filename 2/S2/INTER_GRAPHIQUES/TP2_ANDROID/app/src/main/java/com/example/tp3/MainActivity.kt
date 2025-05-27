package com.example.tp3

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.tp3.ui.theme.TP3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TP3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) {  innerPadding ->
//                    Echafaudage(modifier = Modifier.padding(innerPadding))
                    FlagLetterPairerCountdown(this, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}



