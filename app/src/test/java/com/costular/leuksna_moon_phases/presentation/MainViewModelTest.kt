package com.costular.leuksna_moon_phases.presentation

import com.costular.leuksna_moon_phases.domain.model.MoonInfo
import com.costular.leuksna_moon_phases.domain.model.MoonPhase
import com.costular.leuksna_moon_phases.presentation.main.MainInteractor
import com.costular.leuksna_moon_phases.presentation.main.MainViewModel
import com.costular.leuksna_moon_phases.presentation.main.MainViewState
import io.kotlintest.TestCase
import io.kotlintest.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import io.uniflow.android.test.MockedViewObserver
import io.uniflow.android.test.mockObservers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

@ExperimentalCoroutinesApi
class MainViewModelTest : CoroutineTest() {

    lateinit var mainViewModel: MainViewModel
    lateinit var view: MockedViewObserver

    private val mainInteractor = mockk<MainInteractor>(relaxed = true)

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        mainViewModel = MainViewModel(testDispatcherProvider, mainInteractor)
        view = mainViewModel.mockObservers()
    }

    init {
        "Given a success return when get the moon info then should update the view state accordingly" {
            testDispatcher.runBlockingTest {
                // Given
                val moonInfo = MoonInfo(
                    LocalDate.of(2020, 2, 28),
                    MoonPhase.FULL_MOON,
                    1f,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusHours(3).plusMinutes(24)
                )
                coEvery { mainInteractor.getMoonInfo(any()) } returns moonInfo

                // When
                val localDate = LocalDate.now()
                mainViewModel.getMoonInfo(localDate, null, null)

                // Then
                val expected = MainViewState.Success(
                    localDate,
                    moonInfo
                )

                verifySequence {
                    view.hasState(expected)
                }
            }
        }

    }

}