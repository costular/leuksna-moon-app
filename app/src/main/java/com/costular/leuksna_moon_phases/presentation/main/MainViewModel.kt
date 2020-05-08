package com.costular.leuksna_moon_phases.presentation.main

import com.costular.leuksna_moon_phases.domain.model.MoonInfoRequest
import com.costular.leuksna_moon_phases.presentation.calendar.CalendarState
import com.costular.leuksna_moon_phases.presentation.settings.SettingsHelper
import io.uniflow.android.flow.AndroidDataFlow
import io.uniflow.core.flow.actionOn
import kotlinx.coroutines.flow.onEach
import org.threeten.bp.LocalDate

class MainViewModel(
    private val mainInteractor: MainInteractor,
    settingsHelper: SettingsHelper
) : AndroidDataFlow() {

    init {
        action {
            setState {
                MainViewState()
            }
        }

        settingsHelper.observeLocation().onEach { location ->
            // TODO
        }
    }

    fun getMoonInfo(localDate: LocalDate) = actionOn<MainViewState> { state ->
        val moonInfo = mainInteractor.getMoonInfo(
            MoonInfoRequest(
                localDate,
                null,
                null // TODO get this from location
            )
        )

        setState(state.copy(
            date = localDate,
            moonInfo = moonInfo
        ))
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