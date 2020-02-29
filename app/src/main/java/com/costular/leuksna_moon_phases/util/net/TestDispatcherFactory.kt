package com.costular.leuksna_moon_phases.util.net

import kotlinx.coroutines.CoroutineDispatcher

class TestDispatcherFactory(private val testDispatcher: CoroutineDispatcher): DispatcherFactory {

    override val main: CoroutineDispatcher
        get() = testDispatcher

    override val io: CoroutineDispatcher
        get() = testDispatcher

    override val default: CoroutineDispatcher
        get() = testDispatcher

}