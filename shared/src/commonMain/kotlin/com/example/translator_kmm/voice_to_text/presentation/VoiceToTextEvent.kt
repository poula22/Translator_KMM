package com.example.translator_kmm.voice_to_text.presentation

sealed class VoiceToTextEvent {
    data object Close :VoiceToTextEvent()
    data class PermissionResult(
        val isGranted:Boolean,
        val isPermissionDeclined:Boolean
    ): VoiceToTextEvent()
    data class ToggleRecording(val langCode:String): VoiceToTextEvent()
    data object Reset:VoiceToTextEvent()
}