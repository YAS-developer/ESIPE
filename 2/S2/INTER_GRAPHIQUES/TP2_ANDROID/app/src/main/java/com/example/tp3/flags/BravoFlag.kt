package com.example.tp3.flags

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

object BravoFlag : ICSFlag("bravo") {
  override val message = "Je charge, décharge ou transporte des marchandises dangereuses"

  @Composable
  override fun Flag(modifier: Modifier) {
    Box(modifier) {
      androidx.compose.foundation.Canvas(modifier.fillMaxSize()) {
        drawRect(Color.Red)

        val path = androidx.compose.ui.graphics.Path().apply {
          moveTo(size.width, 0f)
          lineTo(size.width, size.height)
          lineTo(size.width * 0.65f, size.height / 2)
          close()
        }

        drawPath(path = path, color = Color.White)
      }
    }
  }
}