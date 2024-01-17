package com.example.translator_kmm.core.domain.util

import kotlinx.coroutines.flow.MutableStateFlow

expect open class CommonMutableStateFlow<T>(
    stateFlow: MutableStateFlow<T>
) : MutableStateFlow<T>

fun <T> MutableStateFlow<T>.toCommonStateFlow() =
    CommonMutableStateFlow(this)