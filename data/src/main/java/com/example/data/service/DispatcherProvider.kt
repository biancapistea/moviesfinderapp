package com.example.data.service

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

interface DispatchersProvider {
    fun io(): CoroutineDispatcher

    fun main(): CoroutineDispatcher

    fun default(): CoroutineDispatcher

    fun unconfined(): CoroutineDispatcher
}

internal class RuntimeDispatchersProvider @Inject constructor(): DispatchersProvider {
    override fun io() = Dispatchers.IO

    override fun main() = Dispatchers.Main

    override fun default() = Dispatchers.Default

    override fun unconfined() = Dispatchers.Unconfined
}