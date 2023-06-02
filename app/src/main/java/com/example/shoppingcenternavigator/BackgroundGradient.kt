package com.example.shoppingcenternavigator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import com.example.shoppingcenternavigator.ui.theme.blueHosta
import com.example.shoppingcenternavigator.ui.theme.purplishPink

@Composable
fun LinearGradient() {
    val gradient = Brush.linearGradient(
        0.0f to blueHosta,
        500.0f to purplishPink,
        start = Offset.Zero,
        end = Offset.Infinite
    )
    Box(modifier = Modifier.background(gradient))
}

@Composable
fun VerticalGradient() {
    val gradient = Brush.verticalGradient(
        0.0f to blueHosta,
        1.0f to purplishPink,
        startY = 0.0f,
        endY = 1500.0f
    )
    Box(modifier = Modifier.background(gradient))
}