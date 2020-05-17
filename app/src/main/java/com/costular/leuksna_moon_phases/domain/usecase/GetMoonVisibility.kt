package com.costular.leuksna_moon_phases.domain.usecase

import com.costular.leuksna_moon_phases.domain.MoonRepository
import com.costular.leuksna_moon_phases.domain.model.MoonInfoRequest
import org.threeten.bp.LocalDate

class GetMoonVisibility(
    private val moonRepository: MoonRepository
) : UseCase<GetMoonVisibility.Params, Int>() {

    data class Params(
        val date: LocalDate,
        val latitude: Double?,
        val longitude: Double?
    )

    override suspend fun doWork(input: Params): Int =
        moonRepository.getMoonVisibility(
            MoonInfoRequest(
                input.date,
                input.latitude,
                input.longitude
            )
        )

}