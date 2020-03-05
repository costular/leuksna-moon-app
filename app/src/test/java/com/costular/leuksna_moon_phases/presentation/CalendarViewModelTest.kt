package com.costular.leuksna_moon_phases.presentation

import com.costular.leuksna_moon_phases.presentation.calendar.CalendarState
import com.costular.leuksna_moon_phases.presentation.calendar.CalendarViewModel
import com.costular.leuksna_moon_phases.presentation.main.MainViewModel
import io.kotlintest.TestCase
import io.mockk.verifySequence
import io.uniflow.android.test.MockedViewObserver
import io.uniflow.android.test.mockObservers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.threeten.bp.LocalDate

@ExperimentalCoroutinesApi
class CalendarViewModelTest : CoroutineTest() {

    lateinit var calendarViewModel: CalendarViewModel
    lateinit var view: MockedViewObserver

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        calendarViewModel = CalendarViewModel()
        view = calendarViewModel.mockObservers()
    }

    init {
        "When a date is selected then the state should be updated" {
            testDispatcher.runBlockingTest {
                // Given
                val selectDate = LocalDate.now().plusDays(3)

                // When
                calendarViewModel.selectDate(selectDate)

                // Then
                verifySequence {
                    view.hasState(CalendarState(selectDate))
                }
            }
        }
    }

}