package com.example.tp3.flags

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import com.example.tp3.flags.ICSFlag

object MikeFlag: ICSFlag("mike") {
    override val message = "Mon navire est stoppé, n'a plus d'erre"

    @Composable
    override fun Flag(modifier: Modifier) {
        Box(modifier.background(Color.Blue).clip(RectangleShape)) {
            Canvas(modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height

                drawLine(
                    color = Color.White,
                    start = Offset(0f, 0f),
                    end = Offset(width, height),
                    strokeWidth = width / 5
                )

                drawLine(
                    color = Color.White,
                    start = Offset(width, 0f),
                    end = Offset(0f, height),
                    strokeWidth = width / 5
                )
            }
        }
    }
}