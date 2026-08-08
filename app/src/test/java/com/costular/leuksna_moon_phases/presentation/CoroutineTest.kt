package com.costular.leuksna_moon_phases.presentation

import com.costular.leuksna_moon_phases.TestCoroutineDispatchers
import io.uniflow.core.dispatcher.ApplicationDispatchers
import io.uniflow.core.dispatcher.UniFlowDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.After
import org.junit.Before

@OptIn(ExperimentalCoroutinesApi::class)
abstract class CoroutineTest {
    protected lateinit var testDispatcher: TestDispatcher

    @Before
    fun setUpCoroutineDispatcher() {
        testDispatcher = UnconfinedTestDispatcher()
        UniFlowDispatcher.dispatcher = TestCoroutineDispatchers(testDispatcher)
    }

    @After
    fun resetCoroutineDispatcher() {
        UniFlowDispatcher.dispatcher = ApplicationDispatchers()
    }
}
