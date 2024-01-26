package com.example.translator_kmm.android.voice_to_text.domain

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.SpeechRecognizer.ERROR_CLIENT
import com.example.translator_kmm.android.R
import com.example.translator_kmm.core.domain.util.CommonStateFlow
import com.example.translator_kmm.core.domain.util.toCommonFlow
import com.example.translator_kmm.core.domain.util.toCommonMutableStateFlow
import com.example.translator_kmm.core.domain.util.toCommonStateFlow
import com.example.translator_kmm.voice_to_text.domain.VoiceToTextParser
import com.example.translator_kmm.voice_to_text.domain.VoiceToTextState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class AndroidVoiceToTextParser(
    private val app: Application,
) : VoiceToTextParser, RecognitionListener {

    private val recognizer = SpeechRecognizer.createSpeechRecognizer(app)
    private val _state = MutableStateFlow(VoiceToTextState())
    override val state: CommonStateFlow<VoiceToTextState>
        get() = _state.toCommonStateFlow()
    override fun startListening(langCode: String) {
        _state.update { VoiceToTextState() }
        if (!SpeechRecognizer.isRecognitionAvailable(app)) {
            _state.update {
                it.copy(
                    error = app.getString(R.string.speech_recognizer_is_not_available)
                )
            }
            return
        }
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, langCode)
        }
        recognizer.setRecognitionListener(this)
        recognizer.startListening(intent)
        _state.update {
            it.copy(
                isSpeaking = true
            )
        }
    }
    override fun stopListening() {
        _state.update {
            VoiceToTextState()
        }
        recognizer.stopListening()
    }
    override fun reset() {
        _state.value = VoiceToTextState()
    }
    override fun cancel() {
        recognizer.cancel()
    }
    override fun onReadyForSpeech(params: Bundle?) {
        _state.update { it.copy(error = null) }
    }
    override fun onBeginningOfSpeech() = Unit
    override fun onRmsChanged(rmsdB: Float) {
        _state.update {
            it.copy(
                powerRatio = rmsdB * (1f / (12f - (-2f)))
            )
        }
    }
    override fun onBufferReceived(buffer: ByteArray?) = Unit
    override fun onEndOfSpeech() {
        _state.update {
            it.copy(
                isSpeaking = false
            )
        }
    }
    override fun onError(error: Int) {
        if (error == ERROR_CLIENT){
            return
        }
        _state.update {
            it.copy(
                error = "Error: $error"
            )
        }
    }
    override fun onResults(results: Bundle?) {
        results
            ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            ?.getOrNull(0)
            ?.let { value ->
                _state.update {
                    it.copy(
                        result = value
                    )
                }
            }
    }
    override fun onPartialResults(partialResults: Bundle?) = Unit
    override fun onEvent(eventType: Int, params: Bundle?) = Unit
}