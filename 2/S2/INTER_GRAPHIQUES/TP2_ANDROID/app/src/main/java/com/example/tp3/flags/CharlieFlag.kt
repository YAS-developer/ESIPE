package com.example.tp3.flags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.tp3.flags.ICSFlag

object CharlieFlag: ICSFlag("charlie") {
    override val message = "Oui (affirmation positive)"

    @Composable
    override fun Flag(modifier: Modifier) {
        Column(modifier) {
            Box(Modifier.fillMaxWidth().weight(1f).background(Color.Blue))
            Box(Modifier.fillMaxWidth().weight(1f).background(Color.White))
            Box(Modifier.fillMaxWidth().weight(1f).background(Color.Red))
            Box(Modifier.fillMaxWidth().weight(1f).background(Color.White))
            Box(Modifier.fillMaxWidth().weight(1f).background(Color.Blue))
        }
    }
}