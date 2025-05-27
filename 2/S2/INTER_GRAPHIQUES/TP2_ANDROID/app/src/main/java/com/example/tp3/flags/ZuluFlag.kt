package com.example.tp3.flags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

object ZuluFlag: ICSFlag("zulu") {
    override val message = "J'ai besoin d'un remorqueur. Par un navire de pêche : « Je mets mes filets à l'eau »."
    private val triangle1 = GenericShape { size, _ ->
        moveTo(0f, 0f)
        lineTo(size.width, size.height / 2)
        lineTo(0f, size.height)
        lineTo(0f, 0f)
        close()
    }
    private val triangle2 = GenericShape { size, _ ->
        moveTo(size.width, 0f)
        lineTo(0f, size.height / 2)
        lineTo(size.width, size.height)
        lineTo(size.width, 0f)
        close()
    }
    @Composable
    override fun Flag(modifier: Modifier) {
        Box(modifier.fillMaxSize()) {
            Column(Modifier.fillMaxSize()) {
                Box(Modifier.fillMaxWidth().weight(1f/2f, fill=true).background(color=Color.Yellow))
                Box(Modifier.fillMaxWidth().weight(1f/2f, fill=true).background(color=Color.Red))
            }
            Row(Modifier.fillMaxSize()) {
                Box(Modifier.fillMaxHeight().weight(1f/2f, fill=true).background(color=Color.Black, shape= triangle1))
                Box(Modifier.fillMaxHeight().weight(1f/2f, fill=true).background(color=Color.Blue, shape= triangle2))
            }
        }
    }
}
