package com.example.tp3.flags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

object UniformFlag: ICSFlag("uniform") {
    override val message = "Vous courez vers un danger."

    @Composable
    override fun Flag(modifier: Modifier) {
        Column(modifier) {
            repeat(2) { row ->
                Row(Modifier.fillMaxWidth().weight(1f)) {
                    repeat(2) { col ->
                        val color = if ((row + col) % 2 == 0) Color.Red else Color.White
                        Box(
                            Modifier
                                .fillMaxHeight()
                                .weight(1f)
                                .background(color)
                        )
                    }
                }
            }
        }
    }

}