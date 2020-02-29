package com.costular.leuksna_moon_phases.domain.interactor

import com.costular.leuksna_moon_phases.domain.MoonRepository
import com.costular.leuksna_moon_phases.domain.model.MoonInfo
import com.costular.leuksna_moon_phases.domain.model.MoonInfoRequest
import kotlinx.coroutines.CoroutineDispatcher
import org.threeten.bp.LocalDate

class GetMoonInfoInteractor(
    private val moonRepository: MoonRepository
) : Interactor<GetMoonInfoInteractor.Params, MoonInfo>() {

    data class Params(
        val date: LocalDate,
        val latitude: Double?,
        val longitude: Double?
    )

    override suspend fun doWork(input: Params): MoonInfo =
        moonRepository.getMoonInfo(
            MoonInfoRequest(
                input.date,
                input.latitude,
                input.longitude
            )
        )

}