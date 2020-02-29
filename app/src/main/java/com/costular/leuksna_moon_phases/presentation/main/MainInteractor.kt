package com.costular.leuksna_moon_phases.presentation.main

import com.costular.leuksna_moon_phases.domain.interactor.GetMoonInfoInteractor
import com.costular.leuksna_moon_phases.domain.model.MoonInfo
import com.costular.leuksna_moon_phases.domain.model.MoonInfoRequest

interface MainInteractor {
    suspend fun getMoonInfo(moonInfoRequest: MoonInfoRequest): MoonInfo
}

class MainInteractorImpl(
    private val moonInfoInteractor: GetMoonInfoInteractor
) : MainInteractor {

    override suspend fun getMoonInfo(moonInfoRequest: MoonInfoRequest): MoonInfo =
        moonInfoInteractor.execute(
            GetMoonInfoInteractor.Params(
                moonInfoRequest.date,
                moonInfoRequest.latitude,
                moonInfoRequest.longitude
            )
        )

}