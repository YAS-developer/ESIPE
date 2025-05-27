package com.example.tp3.flags

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


object WhiskeyFlag: ICSFlag("whiskey") {
    override val message = "J’ai besoin d’assistance médicale."

    @Composable
    override fun Flag(modifier: Modifier) {
        Canvas(modifier = modifier.background(Color.Blue)) {
            val rectSize1 = size.div(4f)
            val rectSize2 = size.div(1.5f)

            val topLeft1 = Offset(
                (size.width - rectSize1.width) / 2, (size.height - rectSize1.height) / 2
            )

            val topLeft2 = Offset(
                (size.width - rectSize2.width) / 2, (size.height - rectSize2.height) / 2
            )

            drawRect(Color.White, size = rectSize2, topLeft = topLeft2)
            drawRect(Color.Red, size = rectSize1, topLeft = topLeft1)
        }
    }
}