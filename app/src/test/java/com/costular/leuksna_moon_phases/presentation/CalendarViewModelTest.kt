package com.costular.leuksna_moon_phases.presentation

import com.costular.leuksna_moon_phases.presentation.calendar.CalendarState
import com.costular.leuksna_moon_phases.presentation.calendar.CalendarViewModel
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
                val selectDate = LocalDate.now()

                // When
                calendarViewModel.selectDate(selectDate)

                // Then
                verifySequence {
                    view.hasState(CalendarState(selectDate))
                }
            }
        }

        "When a date is selected & it's different from today then should create an initial state with today and then update it with the selected one" {
            testDispatcher.runBlockingTest {
                // Given
                val selectedDate = LocalDate.now().plusDays(2)

                // When
                calendarViewModel.selectDate(selectedDate)

                // Then
                verifySequence {
                    view.hasState(CalendarState())
                    view.hasState(CalendarState(selectedDate))
                }
            }
        }
    }

}
