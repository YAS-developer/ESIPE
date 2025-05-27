package com.example.tp3.flags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import com.example.tp3.flags.ICSFlag

object YankeeFlag: ICSFlag("yankee") {
  override val message = "Mon ancre chasse."

  @Composable
  override fun Flag(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize().clip(RectangleShape)) {
      Row(modifier = Modifier.fillMaxSize().rotate(45f).scale(2f)) {
        repeat(18) { i ->
          Box(modifier = Modifier.weight(1f).fillMaxHeight().background(if (i % 2 == 0) Color.Red else Color.Yellow)
          )
        }
      }
    }
  }
}