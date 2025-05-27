package com.example.tp3.flags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.tp3.flags.ICSFlag

object HotelFlag: ICSFlag("hotel") {
    override val message = "J’ai un pilote à bord."

    @Composable
    override fun Flag(modifier: Modifier) {
        Row(modifier) {
            Box(Modifier.weight(1f).fillMaxHeight().background(Color.White))
            Box(Modifier.weight(1f).fillMaxHeight().background(Color.Red))
        }
    }
}