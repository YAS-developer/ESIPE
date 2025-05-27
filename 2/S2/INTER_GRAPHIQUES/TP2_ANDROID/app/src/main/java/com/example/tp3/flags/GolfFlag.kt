package com.example.tp3.flags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.tp3.flags.ICSFlag

object GolfFlag  : ICSFlag("golf") {
    override val message  = "J’ai besoin d’un pilote. Fait par un bateau de pêche : « Je relève mes filets."

    @Composable
    override fun Flag(modifier: Modifier) {
        Row(modifier) {
            Box(Modifier.Companion.fillMaxSize().weight(1f).background(Color.Companion.Yellow))
            Box(Modifier.Companion.fillMaxSize().weight(1f).background(Color.Companion.Blue))
            Box(Modifier.Companion.fillMaxSize().weight(1f).background(Color.Companion.Yellow))
            Box(Modifier.Companion.fillMaxSize().weight(1f).background(Color.Companion.Blue))
            Box(Modifier.Companion.fillMaxSize().weight(1f).background(Color.Companion.Yellow))
            Box(Modifier.Companion.fillMaxSize().weight(1f).background(Color.Companion.Blue))
        }
    }
}


