package com.costular.leuksna_moon_phases.presentation.main

import androidx.lifecycle.viewModelScope
import com.costular.leuksna_moon_phases.domain.model.Location
import com.costular.leuksna_moon_phases.domain.model.MoonInfoRequest
import com.costular.leuksna_moon_phases.presentation.settings.SettingsHelper
import io.uniflow.android.AndroidDataFlow
import io.uniflow.core.flow.actionOn
import io.uniflow.core.flow.getStateOrNull
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.time.LocalDate

class MainViewModel(
    private val mainInteractor: MainInteractor,
    private val settingsHelper: SettingsHelper
) : AndroidDataFlow(MainViewState()) {

    fun getMoonInfo(
        localDate: LocalDate = getStateOrNull<MainViewState>()?.date ?: LocalDate.now()
    ) = actionOn<MainViewState> { state ->
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

    fun getDayProgress(date: LocalDate): Int = runBlocking {
        val location = settingsHelper.getLocation()
        val latitude = if (location is Location.Set) location.latitude else null
        val longitude = if (location is Location.Set) location.longitude else null

        val moonVisibility = viewModelScope.async {
            mainInteractor.getMoonVisibility(
                MoonInfoRequest(
                    date,
                    latitude,
                    longitude
                )
            )
        }
        moonVisibility.await()
    }

}
