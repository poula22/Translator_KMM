package com.example.translator_kmm.voice_to_text.domain

import com.example.translator_kmm.core.domain.util.CommonStateFlow

interface VoiceToTextParser {
    val state: CommonStateFlow<VoiceToTextState>
    fun startListening(langCode:String)
    fun stopListening()
    fun reset()
    fun cancel()
}