package io.supersimple.common.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface CoroutineDispatchers {
    val main: CoroutineDispatcher
        get() = Dispatchers.Main
    val default: CoroutineDispatcher
        get() = Dispatchers.Default
    val io: CoroutineDispatcher
        get() = Dispatchers.IO
    val unconfined: CoroutineDispatcher
        get() = Dispatchers.Unconfined

    companion object {
        val default: CoroutineDispatchers = object : CoroutineDispatchers {}
    }
}
