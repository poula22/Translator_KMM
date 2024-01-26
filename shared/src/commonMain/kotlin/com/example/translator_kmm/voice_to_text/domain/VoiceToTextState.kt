package com.example.translator_kmm.voice_to_text.domain

data class VoiceToTextState(
    val result: String ="",
    val error: String? = null,
    val powerRatio: Float = 0f,
    val isSpeaking: Boolean = false
)
