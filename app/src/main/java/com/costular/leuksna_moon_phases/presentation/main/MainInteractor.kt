package com.costular.leuksna_moon_phases.presentation.main

import com.costular.leuksna_moon_phases.domain.model.MoonInfo
import com.costular.leuksna_moon_phases.domain.model.MoonInfoRequest
import com.costular.leuksna_moon_phases.domain.usecase.GetMoonInfoUseCase

interface MainInteractor {
    suspend fun getMoonInfo(moonInfoRequest: MoonInfoRequest): MoonInfo
}

class MainInteractorImpl(
    private val moonInfoInteractor: GetMoonInfoUseCase
) : MainInteractor {

    override suspend fun getMoonInfo(moonInfoRequest: MoonInfoRequest): MoonInfo =
        moonInfoInteractor.execute(
            GetMoonInfoUseCase.Params(
                moonInfoRequest.date,
                moonInfoRequest.latitude,
                moonInfoRequest.longitude
            )
        )
}
