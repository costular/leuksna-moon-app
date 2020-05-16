package com.costular.leuksna_moon_phases.data

import com.costular.leuksna_moon_phases.data.mapper.SunkalckMapper
import com.costular.leuksna_moon_phases.domain.MoonRepository
import com.costular.leuksna_moon_phases.domain.model.MoonInfo
import com.costular.leuksna_moon_phases.domain.model.MoonInfoRequest
import com.costular.sunkalc.SunKalc

class MoonRepositoryImpl(
    private val sunkalckMapper: SunkalckMapper
) : MoonRepository {

    override suspend fun getMoonInfo(moonInfoRequest: MoonInfoRequest): MoonInfo {
        val localDateTime = moonInfoRequest.date.atTime(0, 0)
        val sunkalc = SunKalc(
            moonInfoRequest.latitude ?: 40.416775, // TODO improve this
            moonInfoRequest.longitude ?: -3.703790, // TODO improve this
            localDateTime
        )
        val moonPhase = sunkalc.getMoonPhase(localDateTime)
        val moonPosition = sunkalc.getMoonPosition(localDateTime)
        val moonTime = sunkalc.getMoonTimes(localDateTime)
        val zodiacSign = sunkalc.getZodiacSign(localDateTime.toLocalDate())
        return sunkalckMapper.map(
            moonInfoRequest.date,
            moonPhase,
            moonPosition,
            moonTime,
            zodiacSign
        )
    }
}
