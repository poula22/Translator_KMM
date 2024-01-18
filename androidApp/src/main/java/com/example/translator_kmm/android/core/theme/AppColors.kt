package com.example.translator_kmm.android.core.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import com.example.translator_kmm.core.presentation.AppColors


val AccentViolet = Color(AppColors.AccentViolet)
val LightBlue = Color(AppColors.LightBlue)
val LightBlueGrey = Color(AppColors.LightBlueGrey)
val TextBlack = Color(AppColors.TextBlack)
val DarkGrey = Color(AppColors.DarkGrey)

private val lightColors = lightColorScheme (
    primary = AccentViolet,
    background = LightBlueGrey,
    onPrimary = Color.White,
    onBackground = TextBlack,
    surface = Color.White,
    onSurface = TextBlack
)

private val darkColors = darkColorScheme (
    primary = AccentViolet,
    background = DarkGrey,
    onPrimary = Color.White,
    onBackground = Color.White,
    surface = DarkGrey,
    onSurface = Color.White
)

fun getColors(isDarkModeOn:Boolean) : ColorScheme {
    return if (isDarkModeOn) darkColors else lightColors
}