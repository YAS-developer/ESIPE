package com.example.tp3.flags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.tp3.flags.ICSFlag

object KiloFlag: ICSFlag("kilo") {
    override val message = "Je désire entrer en communication avec vous ou vous invite à transmettre."

    @Composable
    override fun Flag(modifier: Modifier) {
        Row(modifier = modifier.fillMaxSize()) {
            Box(Modifier.fillMaxSize().weight(1f).background(Color.Yellow))
            Box(Modifier.fillMaxSize().weight(1f).background(Color.Blue))
        }
    }
}