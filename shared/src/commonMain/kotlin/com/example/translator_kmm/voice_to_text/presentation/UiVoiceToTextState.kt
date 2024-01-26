package com.example.translator_kmm.voice_to_text.presentation

data class UiVoiceToTextState(
    val powerRatios: List<Float> = emptyList(),
    val spokenText: String = "",
    val canRecord: Boolean = false,
    val recordError: String? =null,
    val displayState: DisplayState = DisplayState.WAITING_TO_TALK
)

enum class DisplayState{
    WAITING_TO_TALK,
    SPEAKING,
    DISPLAY_RESULT,
    ERROR
}
