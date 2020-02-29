package com.costular.leuksna_moon_phases

import io.uniflow.core.dispatcher.UniFlowDispatcherConfiguration
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher

class TestCoroutineDispatchers(
    private val testCoroutineDispatcher: TestCoroutineDispatcher
): UniFlowDispatcherConfiguration {
    override fun default(): CoroutineDispatcher = testCoroutineDispatcher
    override fun io(): CoroutineDispatcher = testCoroutineDispatcher
    override fun main(): CoroutineDispatcher = testCoroutineDispatcher
}