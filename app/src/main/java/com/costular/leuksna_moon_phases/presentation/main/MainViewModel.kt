package com.costular.leuksna_moon_phases.presentation.main

import com.costular.leuksna_moon_phases.domain.model.MoonInfoRequest
import io.uniflow.android.flow.AndroidDataFlow
import io.uniflow.core.flow.UIState
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

}