package com.example.tp3.flags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.tp3.flags.ICSFlag

object JulietFlag: ICSFlag("juliet") {
    override val message = "En feu et je transporte des marchandises dangereuses"

    @Composable
    override fun Flag(modifier: Modifier) {
        Column(modifier) {
            Box(Modifier.weight(1f).fillMaxWidth().background(Color.Blue))
            Box(Modifier.weight(1f).fillMaxWidth().background(Color.White))
            Box(Modifier.weight(1f).fillMaxWidth().background(Color.Blue))
        }
    }
}