package com.example.translator_kmm.core.domain.util

sealed interface Resource<T> {
    data class Success<T>(val data: T) : Resource<T>
    data class Error<T>(val throwable: Throwable) : Resource<T>
}