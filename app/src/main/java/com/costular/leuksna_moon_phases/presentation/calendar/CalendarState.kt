package com.costular.leuksna_moon_phases.presentation.calendar

import io.uniflow.core.flow.data.UIState
import java.time.LocalDate

data class CalendarState(
    val selectedDate: LocalDate = LocalDate.now()
) : UIState()
