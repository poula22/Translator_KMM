package com.example.translator_kmm.android.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.translator_kmm.android.R

val SfProText = FontFamily(
    Font(
        resId = R.font.sf_pro_text_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.sf_pro_text_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.sf_pro_text_bold,
        weight = FontWeight.Bold
    ),
)

val typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = SfProText,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = SfProText,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = SfProText,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = SfProText,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = SfProText,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
)