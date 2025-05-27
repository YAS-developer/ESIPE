package com.example.tp3.flags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.foundation.layout.Row
import com.example.tp3.flags.ICSFlag


object LimaFlag: ICSFlag("lima") {
    override val message = "Stoppez votre navire immédiatement. "
    @Composable
    override fun Flag(modifier: Modifier) {
        Row(modifier){
            Column (Modifier.fillMaxWidth().weight(1f)){
                Box(
                    Modifier.fillMaxWidth().weight(1f).background(Color.Yellow))
                Box(
                    Modifier.fillMaxWidth().weight(1f).background(Color.Black))
            };
            Column (Modifier.fillMaxWidth().weight(1f)){
                Box(
                    Modifier.fillMaxWidth().weight(1f).background(Color.Black))
                Box(
                    Modifier.fillMaxWidth().weight(1f).background(Color.Yellow))
            };
        }
    }
}