package com.costular.leuksna_moon_phases.presentation.main

import com.costular.leuksna_moon_phases.domain.model.MoonInfo
import com.costular.leuksna_moon_phases.domain.model.MoonInfoRequest
import com.costular.leuksna_moon_phases.domain.usecase.GetMoonInfoUseCase
import com.costular.leuksna_moon_phases.domain.usecase.GetMoonVisibility

interface MainInteractor {
    suspend fun getMoonInfo(moonInfoRequest: MoonInfoRequest): MoonInfo
    suspend fun getMoonVisibility(moonInfoRequest: MoonInfoRequest): Int
}

class MainInteractorImpl(
    private val moonInfoInteractor: GetMoonInfoUseCase,
    private val getMoonVisibility: GetMoonVisibility
) : MainInteractor {

    override suspend fun getMoonInfo(moonInfoRequest: MoonInfoRequest): MoonInfo =
        moonInfoInteractor.execute(
            GetMoonInfoUseCase.Params(
                moonInfoRequest.date,
                moonInfoRequest.latitude,
                moonInfoRequest.longitude
            )
        )

    override suspend fun getMoonVisibility(moonInfoRequest: MoonInfoRequest): Int =
        getMoonVisibility.execute(
            GetMoonVisibility.Params(
                moonInfoRequest.date,
                moonInfoRequest.latitude,
                moonInfoRequest.longitude
            )
        )

}
