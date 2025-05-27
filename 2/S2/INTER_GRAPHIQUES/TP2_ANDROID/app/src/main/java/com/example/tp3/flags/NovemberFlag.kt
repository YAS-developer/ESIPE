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
import com.example.tp3.flags.ICSFlag

object NovemberFlag: ICSFlag("november") {
    override val message = "Non. (réponse négative, ou le signal qui précède doit être compris sous forme négative)."

    @Composable
    override fun Flag(modifier: Modifier) {
        Column(modifier) {
            repeat(4) { it ->
                if (it % 2 == 0) {
                    Row(Modifier.fillMaxWidth().weight(1f)) {
                        repeat(4) {
                            if (it % 2 == 0) {
                                Box(Modifier.fillMaxHeight().weight(1f).background(Color.Blue))
                            } else {
                                Box(Modifier.fillMaxHeight().weight(1f).background(Color.White))
                            }
                        }
                    }
                } else {
                    Row(Modifier.fillMaxWidth().weight(1f)) {
                        repeat(4) {
                            if (it % 2 == 0) {
                                Box(Modifier.fillMaxHeight().weight(1f).background(Color.White))
                            } else {
                                Box(Modifier.fillMaxHeight().weight(1f).background(Color.Blue))
                            }
                        }
                    }
                }
            }
        }
    }
}