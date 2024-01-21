package com.example.translator_kmm.android.translate.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.translator_kmm.core.presentation.UiLanguage

@Composable
fun TranslateTextField(
    fromText: String,
    toText: String,
    isTranslating: Boolean,
    fromLanguage: UiLanguage,
    toLanguage: UiLanguage,
    onTranslateClick: () -> Unit,
    onTextChange: () -> Unit,
    onCopyClick: () -> Unit,
    onCloseClick: () -> Unit,
    onSpeakerClick: () -> Unit,
    onTextFieldClick: () -> Unit,
    modifier: Modifier= Modifier
) {

}

@Composable
fun IdleTranslateTextField(
    fromText: String,
    isTranslating: Boolean,
    onTranslateClick: () -> Unit,
    onTextChange: () -> Unit,
    modifier: Modifier= Modifier
) {

}