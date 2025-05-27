package com.example.tp3.flags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.tp3.flags.ICSFlag

object EchoFlag : ICSFlag("echo") {
    override val message  = "Je viens sur tribord"

    @Composable
    override fun Flag(modifier: Modifier) {
        Column(modifier) {
            Box(Modifier.Companion.fillMaxWidth().weight(1/2f).background(Color.Companion.Blue))
            Box(Modifier.Companion.fillMaxWidth().weight(1/2f).background(Color.Companion.Red))
        }
    }
}


