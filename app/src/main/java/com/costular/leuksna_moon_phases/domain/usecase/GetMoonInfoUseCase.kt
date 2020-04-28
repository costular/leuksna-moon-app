package com.costular.leuksna_moon_phases.domain.usecase

import com.costular.leuksna_moon_phases.domain.MoonRepository
import com.costular.leuksna_moon_phases.domain.model.MoonInfo
import com.costular.leuksna_moon_phases.domain.model.MoonInfoRequest
import com.costular.leuksna_moon_phases.domain.model.mapper.MoonInfoMapper
import org.threeten.bp.LocalDate

class GetMoonInfoUseCase(
    private val moonRepository: MoonRepository
) : UseCase<GetMoonInfoUseCase.Params, MoonInfo>() {

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