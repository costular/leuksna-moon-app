package com.costular.leuksna_moon_phases.data

import com.costular.leuksna_moon_phases.data.mapper.SunkalckMapper
import com.costular.leuksna_moon_phases.domain.MoonRepository
import com.costular.leuksna_moon_phases.domain.model.MoonInfo
import com.costular.leuksna_moon_phases.domain.model.MoonInfoRequest
import com.costular.sunkalc.SunKalc
import kotlin.math.roundToInt

class MoonRepositoryImpl(
    private val sunkalckMapper: SunkalckMapper
) : MoonRepository {

    override suspend fun getMoonInfo(moonInfoRequest: MoonInfoRequest): MoonInfo {
        val sunkalc = createSunkalcWithRequest(moonInfoRequest)

        val moonPhase = sunkalc.getMoonPhase()
        val moonPosition = sunkalc.getMoonPosition()
        val moonTime = sunkalc.getMoonTimes()
        val zodiacSign = sunkalc.getZodiacSign()
        return sunkalckMapper.map(
            moonInfoRequest.date,
            moonPhase,
            moonPosition,
            moonTime,
            zodiacSign
        )
    }

    override suspend fun getMoonVisibility(moonInfoRequest: MoonInfoRequest): Int {
        val sunkalc = createSunkalcWithRequest(moonInfoRequest)
        return (sunkalc.getMoonPhase().fraction * 100).roundToInt()
    }

    private fun createSunkalcWithRequest(moonInfoRequest: MoonInfoRequest): SunKalc {
        val localDateTime = moonInfoRequest.date.atTime(0, 0)
        val sunkalc = SunKalc(
            moonInfoRequest.latitude ?: 40.416775, // TODO improve this
            moonInfoRequest.longitude ?: -3.703790, // TODO improve this
            localDateTime
        )
        return sunkalc
    }

}
