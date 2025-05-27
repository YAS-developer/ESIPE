package com.example.tp3.flags

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

object OscarFlag: ICSFlag("oscar") {
    override val message = "Ne me gênez pas, je manœuvre avec difficulté"

    @Composable
    override fun Flag(modifier: Modifier) {
        Canvas(modifier) {
            val width = size.width
            val height = size.height

            drawPath(
                path = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(width, 0f)
                    lineTo(width, height)
                    close()
                },
                color = Color.Red
            )

            drawPath(
                path = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(0f, height)
                    lineTo(width, height)
                    close()
                },
                color = Color.Yellow
            )
        }
    }
}