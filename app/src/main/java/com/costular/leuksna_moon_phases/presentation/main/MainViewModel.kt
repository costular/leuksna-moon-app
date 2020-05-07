package com.costular.leuksna_moon_phases.presentation.main

import com.costular.leuksna_moon_phases.domain.model.MoonInfoRequest
import com.costular.leuksna_moon_phases.presentation.calendar.CalendarState
import com.costular.leuksna_moon_phases.presentation.settings.SettingsHelper
import io.uniflow.android.flow.AndroidDataFlow
import io.uniflow.core.flow.UIEvent
import io.uniflow.core.flow.UIState
import io.uniflow.core.flow.fromState
import io.uniflow.core.flow.getStateAs
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

    fun getMoonInfo(localDate: LocalDate) = fromState<MainViewState> { state ->
        val moonInfo = mainInteractor.getMoonInfo(
            MoonInfoRequest(
                localDate,
                null,
                null // TODO get this from location
            )
        )

        state.copy(
            date = localDate,
            moonInfo = moonInfo
        )
    }

    fun selectDate(newDate: LocalDate) {
        getMoonInfo(newDate)
    }

    fun openCalendar() = setState {
        val selectedDate = getStateAs<MainViewState>().date
        sendEvent(MainEvents.OpenCalendar(selectedDate))
    }

    fun openSettings() = setState {
        sendEvent(MainEvents.OpenSettings)
    }

}