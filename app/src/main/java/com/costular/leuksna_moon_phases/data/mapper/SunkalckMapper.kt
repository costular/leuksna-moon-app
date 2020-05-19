package com.costular.leuksna_moon_phases.data.mapper

import com.costular.leuksna_moon_phases.data.model.MoonInfoDTO
import com.costular.leuksna_moon_phases.domain.model.MoonInfo
import com.costular.leuksna_moon_phases.domain.model.Zodiac
import com.costular.leuksna_moon_phases.domain.model.mapper.MoonInfoMapper
import com.costular.sunkalc.*
import com.costular.leuksna_moon_phases.domain.model.MoonPhase as LeuksnaPhase
import org.threeten.bp.LocalDate

class SunkalckMapper(private val moonInfoMapper: MoonInfoMapper) {

    fun map(
        date: LocalDate,
        moonPhaseInfo: MoonPhaseInfo,
        moonPosition: MoonPosition,
        moonTime: MoonTime,
        zodiacSign: ZodiacSign
    ): MoonInfo =
        moonInfoMapper.map(
            MoonInfoDTO(
                date,
                when (moonPhaseInfo.phaseName) {
                    MoonPhase.NEW_MOON -> LeuksnaPhase.NEW_MOON
                    MoonPhase.FIRST_QUARTER -> LeuksnaPhase.FIRST_QUARTER
                    MoonPhase.FULL_MOON -> LeuksnaPhase.FULL_MOON
                    MoonPhase.LAST_QUARTER -> LeuksnaPhase.LAST_QUARTER
                    MoonPhase.WANING_CRESCENT -> LeuksnaPhase.WANING_CRESCENT
                    MoonPhase.WANING_GIBBOUS -> LeuksnaPhase.WANING_GIBBOUS
                    MoonPhase.WAXING_CRESCENT -> LeuksnaPhase.WAXING_CRESCENT
                    MoonPhase.WAXING_GIBBOUS -> LeuksnaPhase.WAXING_GIBBOUS
                },
                moonPhaseInfo.fraction.toFloat(),
                moonPosition.distanceKm,
                moonPosition.altitude,
                when (zodiacSign) {
                    ZodiacSign.AQUARIUS -> Zodiac.AQUARIUS
                    ZodiacSign.ARIES -> Zodiac.ARIES
                    ZodiacSign.CANCER -> Zodiac.CANCER
                    ZodiacSign.CAPRICORN -> Zodiac.CAPRICORN
                    ZodiacSign.GEMINI -> Zodiac.GEMINI
                    ZodiacSign.LEO -> Zodiac.LEO
                    ZodiacSign.LIBRA -> Zodiac.LIBRA
                    ZodiacSign.PISCES -> Zodiac.PISCES
                    ZodiacSign.SAGITTARIUS -> Zodiac.SAGITTARIUS
                    ZodiacSign.SCORPIO -> Zodiac.SCORPIO
                    ZodiacSign.VIRGO -> Zodiac.VIRGO
                },
                moonTime.rise,
                moonTime.set
            )
        )
}