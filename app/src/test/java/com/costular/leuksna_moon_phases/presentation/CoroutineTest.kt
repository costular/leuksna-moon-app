package com.costular.leuksna_moon_phases.presentation

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.costular.leuksna_moon_phases.TestCoroutineDispatchers
import com.costular.leuksna_moon_phases.util.net.TestDispatcherFactory
import io.kotlintest.TestCase
import io.kotlintest.TestResult
import io.kotlintest.specs.StringSpec
import io.uniflow.core.dispatcher.ApplicationDispatchers
import io.uniflow.core.dispatcher.UniFlowDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain


@ExperimentalCoroutinesApi
abstract class CoroutineTest(
    protected val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : StringSpec() {

    val testDispatcherProvider = TestDispatcherFactory(testDispatcher)

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        Dispatchers.setMain(testDispatcher)
        UniFlowDispatcher.dispatcher = TestCoroutineDispatchers(testDispatcher)

        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread(): Boolean {
                return true
            }
        })
    }

    override fun afterTest(testCase: TestCase, result: TestResult) {
        super.afterTest(testCase, result)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
        UniFlowDispatcher.dispatcher = ApplicationDispatchers()
        ArchTaskExecutor.getInstance().setDelegate(null)
    }

}