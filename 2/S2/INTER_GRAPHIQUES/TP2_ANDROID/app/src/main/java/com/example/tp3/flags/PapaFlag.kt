package com.example.tp3.flags

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

object PapaFlag : ICSFlag("papa") {
    override val message =
        "1) Au port : « Toutes les personnes doivent se présenter à bord, le navire doit prendre la mer. » ;\n" + "2) A la mer, fait par un bateau de pêche : « Mes filets sont accrochés par un obstacle. » ;\n" + "3) A la mer (voie sonore) : « J’ai besoin d’un pilote. »"

    @Composable
    override fun Flag(modifier: Modifier) {
        Canvas(modifier = modifier.background(Color.Blue)) {
            val rectSize = size.div(2f)

            val topLeft = Offset(
                (size.width - rectSize.width) / 2, (size.height - rectSize.height) / 2
            )

            drawRect(Color.White, size = rectSize, topLeft = topLeft)
        }
    }
}