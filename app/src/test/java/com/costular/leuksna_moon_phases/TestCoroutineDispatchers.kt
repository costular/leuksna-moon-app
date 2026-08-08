package com.costular.leuksna_moon_phases

import io.uniflow.core.dispatcher.UniFlowDispatcherConfiguration
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestDispatcher

class TestCoroutineDispatchers(
    private val testDispatcher: TestDispatcher
): UniFlowDispatcherConfiguration {
    override fun default(): CoroutineDispatcher = testDispatcher
    override fun io(): CoroutineDispatcher = testDispatcher
    override fun main(): CoroutineDispatcher = testDispatcher
}
