package com.costular.leuksna_moon_phases.presentation

import com.costular.leuksna_moon_phases.domain.model.MoonInfo
import com.costular.leuksna_moon_phases.domain.model.MoonPhase
import com.costular.leuksna_moon_phases.domain.model.Zodiac
import com.costular.leuksna_moon_phases.presentation.main.MainEvents
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
        mainViewModel = MainViewModel(mainInteractor)
        view = mainViewModel.mockObservers()
    }

    init {
        "Given a success return when get the moon info then should update the view state accordingly" {
            testDispatcher.runBlockingTest {
                // Given
                val moonInfo = MoonInfo(
                    LocalDate.of(2020, 2, 28),
                    MoonPhase.FULL_MOON,
                    100,
                    100.0,
                    100.0,
                    Zodiac.GEMINI,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusHours(3).plusMinutes(24)
                )
                coEvery { mainInteractor.getMoonInfo(any()) } returns moonInfo

                // When
                val localDate = LocalDate.now()
                mainViewModel.getMoonInfo(localDate, null, null)

                // Then
                val expected = MainViewState(
                    localDate,
                    moonInfo
                )

                verifySequence {
                    view.hasState(expected)
                }
            }
        }

        "When click on calendar then should navigate to calendar screen" {
            // Given

            // When
            mainViewModel.openCalendar()

            // Then
            verifySequence {
                view.hasEvent(MainEvents.OpenCalendar)
            }
        }

        "When click on settings then should navigate to settings screen" {
            // Given

            // When
            mainViewModel.openSettings()

            // Then
            verifySequence {
                view.hasEvent(MainEvents.OpenSettings)
            }
        }
    }

}