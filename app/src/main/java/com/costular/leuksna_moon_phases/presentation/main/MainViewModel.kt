package com.costular.leuksna_moon_phases.presentation.main

import com.costular.leuksna_moon_phases.di.settings
import com.costular.leuksna_moon_phases.domain.model.Location
import com.costular.leuksna_moon_phases.domain.model.MoonInfoRequest
import com.costular.leuksna_moon_phases.presentation.calendar.CalendarState
import com.costular.leuksna_moon_phases.presentation.settings.SettingsHelper
import io.uniflow.android.flow.AndroidDataFlow
import io.uniflow.core.flow.actionOn
import kotlinx.coroutines.flow.onEach
import org.threeten.bp.LocalDate

class MainViewModel(
    private val mainInteractor: MainInteractor,
    private val settingsHelper: SettingsHelper
) : AndroidDataFlow(MainViewState()) {

    fun getMoonInfo(localDate: LocalDate) = actionOn<MainViewState> { state ->
        val location = settingsHelper.getLocation()
        val latitude = if (location is Location.Set) location.latitude else null
        val longitude = if (location is Location.Set) location.longitude else null

        val moonInfo = mainInteractor.getMoonInfo(
            MoonInfoRequest(
                localDate,
                latitude,
                longitude
            )
        )

        setState(
            state.copy(
                date = localDate,
                moonInfo = moonInfo
            )
        )
    }

    fun selectDate(newDate: LocalDate) {
        getMoonInfo(newDate)
    }

    fun openCalendar() = actionOn<MainViewState> { state ->
        val selectedDate = state.date
        sendEvent(MainEvents.OpenCalendar(selectedDate))
    }

    fun openSettings() = action {
        sendEvent(MainEvents.OpenSettings)
    }

}