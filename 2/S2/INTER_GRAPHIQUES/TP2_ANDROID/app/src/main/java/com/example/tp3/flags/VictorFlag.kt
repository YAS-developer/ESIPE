package com.example.tp3.flags

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview

object VictorFlag : ICSFlag("victor") {
    override val message = "Je demande assistance."

    @Composable
    override fun Flag(modifier: Modifier) {
        Canvas(modifier
            .fillMaxSize()
            .background(Color.White)
            .clip(RectangleShape)) {
            val width = size.width
            val height = size.height

            drawLine(
                color = Color.Red,
                start = Offset(0f, 0f),
                end = Offset(width, height),
                strokeWidth = width / 5
            )

            drawLine(
                color = Color.Red,
                start = Offset(width, 0f),
                end = Offset(0f, height),
                strokeWidth = width / 5
            )
        }
    }

}