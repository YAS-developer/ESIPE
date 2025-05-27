package com.example.tp3.flags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

object FoxtrotFlag: ICSFlag("foxtrot") {
    override val message = "Je suis désemparé, communiquez avec moi."
    private val triangle1 = GenericShape { size, _ ->
        moveTo(0f, 0f)
        lineTo(size.width, 0f)
        lineTo(0f, size.height / 2)
        lineTo(size.width, size.height)
        lineTo(0f, size.height)
        lineTo(0f, 0f)
        close()
    }
    private val triangle2 = GenericShape { size, _ ->
        moveTo(size.width, 0f)
        lineTo(0f, 0f)
        lineTo(size.width, size.height / 2)
        lineTo(0f, size.height)
        lineTo(size.width, size.height)
        lineTo(size.width, 0f)
        close()
    }
    @Composable
    override fun Flag(modifier: Modifier) {
        Row(modifier = modifier.background(color=Color.Red)) {
            Box(Modifier.fillMaxHeight().weight(1f/2f, fill=true).background(color=Color.White, shape= triangle1))
            Box(Modifier.fillMaxHeight().weight(1f/2f, fill=true).background(color=Color.White, shape= triangle2))
        }
    }
}