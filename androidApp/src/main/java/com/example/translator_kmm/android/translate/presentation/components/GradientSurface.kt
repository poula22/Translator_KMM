package com.example.translator_kmm.android.translate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush

fun Modifier.gradientSurface() = composed {
    if (isSystemInDarkTheme()){
        this.background(
            brush = Brush.verticalGradient(
                colors = listOf()
            )
        )
    } else this.background(MaterialTheme.colorScheme.surface)
}