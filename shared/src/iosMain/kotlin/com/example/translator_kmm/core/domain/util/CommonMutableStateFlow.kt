package com.example.translator_kmm.core.domain.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

actual open class CommonMutableStateFlow<T> actual constructor(
    private val stateFlow: MutableStateFlow<T>
) : CommonStateFlow<T>(stateFlow),MutableStateFlow<T> {

    override var value: T
        get() = super.value
        set(value){
            stateFlow.value=value
        }

    override val subscriptionCount: StateFlow<Int>
        get() = stateFlow.subscriptionCount

    override fun compareAndSet(expect: T, update: T): Boolean =
       stateFlow.compareAndSet(expect,update)

    @ExperimentalCoroutinesApi
    override fun resetReplayCache() =
        stateFlow.resetReplayCache()

    override fun tryEmit(value: T): Boolean =
        stateFlow.tryEmit(value)

    override suspend fun emit(value: T) =
        stateFlow.emit(value)
}