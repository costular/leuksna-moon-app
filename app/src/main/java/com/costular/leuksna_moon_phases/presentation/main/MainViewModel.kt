package com.costular.leuksna_moon_phases.presentation.main

import com.costular.leuksna_moon_phases.domain.model.MoonInfoRequest
import com.costular.leuksna_moon_phases.presentation.BaseViewModel
import com.costular.leuksna_moon_phases.util.net.DispatcherFactory
import org.threeten.bp.LocalDate

class MainViewModel(
    dispatcherFactory: DispatcherFactory,
    private val mainInteractor: MainInteractor
) : BaseViewModel(dispatcherFactory) {

    fun getMoonInfo(localDate: LocalDate, latitude: Double?, longitude: Double?) = launchOnIO {
        val moonInfo = mainInteractor.getMoonInfo(
            MoonInfoRequest(
                localDate,
                latitude,
                longitude
            )
        )
        setState {
            MainViewState.Success(
                date = localDate,
                moonInfo = moonInfo
            )
        }
    }

}