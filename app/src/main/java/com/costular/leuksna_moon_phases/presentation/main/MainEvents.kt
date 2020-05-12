package com.costular.leuksna_moon_phases.presentation.main

import io.uniflow.core.flow.data.UIEvent
import org.threeten.bp.LocalDate

sealed class MainEvents : UIEvent() {

    data class OpenCalendar(val selectedDate: LocalDate) : MainEvents()

    object OpenSettings : MainEvents()
}
