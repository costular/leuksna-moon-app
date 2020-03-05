package com.costular.leuksna_moon_phases.presentation.main

import com.costular.leuksna_moon_phases.domain.model.MoonInfoRequest
import com.costular.leuksna_moon_phases.presentation.calendar.CalendarState
import io.uniflow.android.flow.AndroidDataFlow
import io.uniflow.core.flow.UIEvent
import io.uniflow.core.flow.UIState
import io.uniflow.core.flow.getStateAs
import org.threeten.bp.LocalDate

class MainViewModel(
    private val mainInteractor: MainInteractor
) : AndroidDataFlow() {

    fun getMoonInfo(localDate: LocalDate, latitude: Double?, longitude: Double?) =
        setState({
            val moonInfo = mainInteractor.getMoonInfo(
                MoonInfoRequest(
                    localDate,
                    latitude,
                    longitude
                )
            )

            MainViewState(
                date = localDate,
                moonInfo = moonInfo
            )
        }, { error -> UIState.Failed(error = error) })

    fun selectDate(newDate: LocalDate) {
        getMoonInfo(newDate, null, null)
    }

    fun openCalendar() = setState {
        val selectedDate = getStateAs<MainViewState>().date
        sendEvent(MainEvents.OpenCalendar(selectedDate))
    }

    fun openSettings() = setState {
        sendEvent(MainEvents.OpenSettings)
    }

}