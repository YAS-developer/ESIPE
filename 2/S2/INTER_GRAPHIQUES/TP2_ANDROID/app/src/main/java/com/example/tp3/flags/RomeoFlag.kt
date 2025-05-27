package com.example.tp3.flags

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

object RomeoFlag: ICSFlag("romeo") {
    override val message = "J’ai reçu votre dernier signal"

    @Composable
    override fun Flag(modifier: Modifier) {

        Canvas(modifier) {
            val width = size.width
            val height = size.height
            val Midllewidth = width/2
            val Midlleheight = height/2
            val ratioWith = width/10
            val ratioHeight = height/10
            drawRect(Color.Red)


            drawPath(
                path = Path().apply {
                    moveTo(Midllewidth - ratioWith, 0f)
                    lineTo(Midllewidth - ratioWith, height)
                    lineTo(Midllewidth + ratioWith, height)
                    lineTo(Midllewidth + ratioWith, 0f)
                    close()
                },
                color = Color.Yellow
            )

            drawPath(
                path = Path().apply {
                    moveTo(0f, Midlleheight - ratioHeight)
                    lineTo(width, Midlleheight - ratioHeight)
                    lineTo(width, Midlleheight + ratioHeight)
                    lineTo(0f, Midlleheight + ratioHeight)
                    close()
                },
                color = Color.Yellow
            )

        }
    }
}