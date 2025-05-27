package com.example.tp3.flags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.tp3.flags.ICSFlag

object IndiaFlag: ICSFlag("india") {
    override val message = "Je viens sur bâbord."
    @Composable
    override fun Flag(modifier: Modifier) {
        Column(modifier = modifier.background(Color.Yellow)) {
            Box(Modifier.fillMaxHeight().weight(1f/4f, fill=true).background(color= Color.Transparent))
            Row(Modifier.fillMaxSize().weight(1f/2f, fill=true)) {
                Box(Modifier.fillMaxHeight().weight(1f/4f, fill=true).background(color= Color.Transparent))
                Box(Modifier.fillMaxHeight().weight(1f/2f, fill=true).background(color=Color.Black, shape=CircleShape))
                Box(Modifier.fillMaxHeight().weight(1f/4f, fill=true).background(color= Color.Transparent))
            }
            Box(Modifier.fillMaxHeight().weight(1f/4f, fill=true).background(color= Color.Transparent))
        }
    }
}