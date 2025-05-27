package com.example.tp3.flags

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

object XrayFlag: ICSFlag("xray") {
    override val message = "Arrêtez vos manœuvres et veillez mes signaux."

    @Composable
    override fun Flag(modifier: Modifier) {

        Canvas(modifier) {
            val width = size.width
            val height = size.height

            val middleWidth = width / 2
            val middleHeight = height / 2

            val bandWidth = width / 10
            val bandHeight = height / 10

            drawRect(Color.White)

            drawRect(
                color = Color.Blue,
                topLeft = Offset(middleWidth - bandWidth, 0f),
                size = Size(bandWidth * 2, height)
            )

            drawRect(
                color = Color.Blue,
                topLeft = Offset(0f, middleHeight - bandHeight),
                size = Size(width, bandHeight * 2)
            )
        }

    }
}