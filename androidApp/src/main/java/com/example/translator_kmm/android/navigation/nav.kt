package com.example.translator_kmm.android.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.translator_kmm.android.core.presentation.Routes
import com.example.translator_kmm.android.translate.presentation.AndroidTranslateViewModel
import com.example.translator_kmm.android.translate.presentation.components.TranslateScreen
import com.example.translator_kmm.android.voice_to_text.presentation.AndroidVoiceToTextViewModel
import com.example.translator_kmm.android.voice_to_text.presentation.component.VoiceToTextScreen
import com.example.translator_kmm.translate.presentation.TranslateEvent
import com.example.translator_kmm.voice_to_text.presentation.VoiceToTextEvent

@Composable
fun TranslateRoot() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.TRANSLATE) {
        composable(route = Routes.TRANSLATE) {
            val viewModel: AndroidTranslateViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()

            val  voiceResult by it.savedStateHandle
                .getStateFlow<String?>("voiceResult",null)
                .collectAsState()

            LaunchedEffect(voiceResult){
                viewModel.onEvent(TranslateEvent.SubmitVoiceResult(voiceResult))
                it.savedStateHandle["voiceResult"] = null
            }

            TranslateScreen(
                state = state,
                onEvent = { event ->
                    when (event) {
                        is TranslateEvent.RecordAudio -> navController.navigate(
                            Routes.VOICE_TO_TEXT + "/${state.fromLanguage.language.langName}"
                        )

                        else -> viewModel.onEvent(event)
                    }

                }
            )
        }
        composable(
            Routes.VOICE_TO_TEXT + "/{languageCode}",
            arguments = listOf(
                navArgument(
                    name = "languageCode"
                ) {
                    type = NavType.StringType
                    defaultValue = "en"
                }
            )
        ) { backStackEntry->
            val langCode = backStackEntry.arguments?.getString("languageCode") ?: "en"
            val viewModel: AndroidVoiceToTextViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            VoiceToTextScreen(
                state = state,
                langCode = langCode,
                onResult = {spokenText->
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "voiceResult",spokenText
                    )
                    navController.popBackStack()
                },
                onEvent ={event->
                    when(event){
                        VoiceToTextEvent.Close -> {
                            navController.popBackStack()
                        }
                        else ->{
                            viewModel.onEvent(event)
                        }
                    }
                }
            )
        }
    }
}