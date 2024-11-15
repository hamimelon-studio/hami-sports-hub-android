package com.mikeapp.sportsmate.ui.animation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun SolidColorLoadingBar(height: Dp, color: Color, backgroundColor: Color) {
    LinearProgressIndicator(
        color = color,
//        backgroundColor = backgroundColor,
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    )
}