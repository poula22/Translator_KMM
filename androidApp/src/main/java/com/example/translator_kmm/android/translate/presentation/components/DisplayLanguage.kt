package com.example.translator_kmm.android.translate.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.translator_kmm.core.presentation.UiLanguage

@Composable
fun DisplayLanguage(
    language: UiLanguage,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SmallIcon(language = language)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = language.language.langName,
            color = Color.LightGray
        )
    }

}