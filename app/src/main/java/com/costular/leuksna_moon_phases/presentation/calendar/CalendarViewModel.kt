package com.costular.leuksna_moon_phases.presentation.calendar

import io.uniflow.android.flow.AndroidDataFlow
import org.threeten.bp.LocalDate

class CalendarViewModel : AndroidDataFlow() {

    private var selectedDate: LocalDate = LocalDate.now()

    fun selectDate(newDate: LocalDate) = setState {
        selectedDate = newDate
        CalendarState(newDate)
    }

}