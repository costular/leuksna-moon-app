package com.costular.leuksna_moon_phases.presentation.calendar

import io.uniflow.android.flow.AndroidDataFlow
import org.threeten.bp.LocalDate

class CalendarViewModel : AndroidDataFlow(CalendarState()) {

    private var selectedDate: LocalDate = LocalDate.now()

    fun selectDate(newDate: LocalDate) {
        if (!selectedDate.isEqual(newDate)) {
            selectedDate = newDate
            action {
                setState {
                    CalendarState(newDate)
                }
            }
        }
    }
}
