package com.costular.leuksna_moon_phases.presentation

import com.costular.leuksna_moon_phases.presentation.calendar.CalendarState
import com.costular.leuksna_moon_phases.presentation.calendar.CalendarViewModel
import io.uniflow.core.flow.getStateOrNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class CalendarViewModelTest : CoroutineTest() {

    private lateinit var calendarViewModel: CalendarViewModel

    @Before
    fun setUp() {
        calendarViewModel = CalendarViewModel()
    }

    @Test
    fun `selecting a new date updates the state`() {
        val selectedDate = LocalDate.now().plusDays(2)

        calendarViewModel.selectDate(selectedDate)
        assertEquals(CalendarState(selectedDate), calendarViewModel.getStateOrNull<CalendarState>())
    }

    @Test
    fun `selecting today keeps the initial state`() {
        calendarViewModel.selectDate(LocalDate.now())
        assertEquals(CalendarState(), calendarViewModel.getStateOrNull<CalendarState>())
    }
}
