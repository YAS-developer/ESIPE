package com.example.tp3.flags

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

object SierraFlag : ICSFlag("sierra") {
    override val message = "Je bats en arrière."

    @Composable
    override fun Flag(modifier: Modifier) {
        Canvas(modifier = modifier.background(Color.White)) {
            val rectSize = size.div(2f)

            val topLeft = Offset(
                (size.width - rectSize.width) / 2, (size.height - rectSize.height) / 2
            )

            drawRect(Color.Blue, size = rectSize, topLeft = topLeft)
        }
    }
}