package com.example.translator_kmm.android.translate.presentation.components

import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import com.example.translator_kmm.android.R
import com.example.translator_kmm.android.core.theme.LightBlue
import com.example.translator_kmm.translate.domain.translate.TranslateError
import com.example.translator_kmm.translate.presentation.TranslateEvent
import com.example.translator_kmm.translate.presentation.TranslateState
import java.util.Locale

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TranslateScreen(
    state: TranslateState,
    onEvent: (TranslateEvent) -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.error) {
        val message = when (state.error) {
            TranslateError.CLIENT_ERROR -> context.getString(R.string.client_error)
            TranslateError.SERVER_ERROR -> context.getString(R.string.error_service_unavailble)
            TranslateError.SERVICE_UNAVAILABLE -> context.getString(R.string.error_service_unavailble)
            TranslateError.UNKNOWN_ERROR -> context.getString(R.string.unkown_error)
            else -> null
        }
        message?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            onEvent(TranslateEvent.OnErrorSeen)
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(TranslateEvent.RecordAudio)
                },
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(35.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(70.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.mic),
                    contentDescription = stringResource(id = R.string.record_audio)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LanguageDropDown(
                        language = state.fromLanguage,
                        isOpen = state.isChoosingFromLanguage,
                        dismiss = {
                            onEvent(TranslateEvent.StopChoosingLanguage)
                        },
                        onSelectedLanguage = {
                            onEvent(TranslateEvent.ChooseFromLanguage(it))
                        },
                        onClick = {
                            onEvent(TranslateEvent.OpenFromLanguageDropDown)
                        }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    SwapLanguagesButton(onClick = {
                        onEvent(TranslateEvent.SwapLanguages)
                    })
                    Spacer(modifier = Modifier.weight(1f))
                    LanguageDropDown(
                        modifier = Modifier.fillMaxWidth(1f),
                        language = state.toLanguage,
                        isOpen = state.isChoosingToLanguage,
                        dismiss = {
                            onEvent(TranslateEvent.StopChoosingLanguage)
                        },
                        onSelectedLanguage = {
                            onEvent(TranslateEvent.ChooseToLanguage(it))
                        },
                        onClick = {
                            onEvent(TranslateEvent.OpenToLanguageDropDown)
                        }
                    )
                }
            }
            item {
                val clipboardManager = LocalClipboardManager.current
                val keyboardController = LocalSoftwareKeyboardController.current
                val textToSpeech = rememberTextToSpeech()

                TranslateTextField(
                    fromText = state.fromText,
                    toText = state.toText,
                    isTranslating = state.isTranslating,
                    fromLanguage = state.fromLanguage,
                    toLanguage = state.toLanguage,
                    onTranslateClick = {
                        keyboardController?.hide()
                        onEvent(TranslateEvent.Translate)
                    },
                    onTextChange = {
                        onEvent(TranslateEvent.ChangeTranslationText(it))
                    },
                    onCopyClick = {
                        clipboardManager.setText(
                            buildAnnotatedString {
                                append(it)
                            }
                        )
                        Toast.makeText(
                            context,
                            context.getString(R.string.copied_to_clipboard),
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    onCloseClick = {
                        onEvent(TranslateEvent.CloseTranslation)
                    },
                    onSpeakerClick = {
                        textToSpeech.language = state.toLanguage.toLocale() ?: Locale.ENGLISH
                        textToSpeech.speak(
                            state.toText,
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            null
                        )
                    },
                    onTextFieldClick = {
                        onEvent(TranslateEvent.EditTranslation)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                if (state.history.isNotEmpty()) {
                    Text(
                        text = stringResource(id = R.string.history),
                        style = MaterialTheme.typography.headlineMedium,
                    )
                }
            }
            items(state.history) { item ->
                TranslateHistoryItem(
                    item = item,
                    onCLick = {
                        onEvent(TranslateEvent.SelectHistoryItem(item))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}