package com.example.translator_kmm.core.domain.util

import kotlinx.coroutines.flow.MutableStateFlow

actual open class CommonMutableStateFlow<T> actual constructor(
    private val stateFlow: MutableStateFlow<T>
):MutableStateFlow<T> by stateFlow