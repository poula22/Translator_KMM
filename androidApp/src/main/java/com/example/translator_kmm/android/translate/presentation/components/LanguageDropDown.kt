package com.example.translator_kmm.android.translate.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.translator_kmm.android.R
import com.example.translator_kmm.android.core.theme.LightBlue
import com.example.translator_kmm.core.presentation.UiLanguage

@Composable
fun LanguageDropDown(
    language: UiLanguage,
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    dismiss: () -> Unit,
    onSelectedLanguage: (UiLanguage) -> Unit,
    onClick: () -> Unit,
) {
    Box(modifier = modifier) {
        DropdownMenu(expanded = isOpen, onDismissRequest = dismiss) {
            Box(modifier = Modifier.size(width = 200.dp, height = 200.dp)) {
                LazyColumn {
                    items(
                        UiLanguage.allLanguages,
                        key = { item: UiLanguage -> item.language.langName }
                    ) { uiLanguage ->
                        LanguageDropDownMenuItem(
                            language = uiLanguage,
                            onClick = {
                                onSelectedLanguage(uiLanguage)
                            },
                            modifier = modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
        Row(
            modifier = modifier
                .clickable(onClick = onClick)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = language.drawableRes,
                contentDescription = language.language.langName,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = language.language.langName,
                color = LightBlue,
            )
            Icon(
                imageVector = if (isOpen) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                contentDescription = if (isOpen) {
                    stringResource(id = R.string.close)
                } else {
                    stringResource(id = R.string.open)
                },
                tint = LightBlue,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LangDropDownPreview() {
    MaterialTheme {
        var isOpen by remember {
            mutableStateOf(false)
        }
        var uiLanguage by remember {
            mutableStateOf(UiLanguage.allLanguages[0])
        }
        LanguageDropDown(
            language = uiLanguage,
            isOpen = isOpen,
            dismiss = { isOpen = false },
            onSelectedLanguage = {
                uiLanguage = it
                isOpen = false
            },
            onClick = { isOpen = true }
        )
    }
}