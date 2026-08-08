package com.costular.leuksna_moon_phases.data

import com.costular.leuksna_moon_phases.data.model.MoonInfoDTO
import com.costular.leuksna_moon_phases.domain.MoonRepository
import com.costular.leuksna_moon_phases.domain.model.MoonInfo
import com.costular.leuksna_moon_phases.domain.model.MoonInfoRequest
import com.costular.leuksna_moon_phases.domain.model.MoonPhase
import com.costular.leuksna_moon_phases.domain.model.Zodiac
import com.costular.leuksna_moon_phases.domain.model.mapper.MoonInfoMapper
import com.costular.leuksna_moon_phases.util.toRadian
import dev.jamesyox.kastro.luna.LunarEvent
import dev.jamesyox.kastro.luna.LunarHorizonEventSequence
import dev.jamesyox.kastro.luna.LunarPhase
import dev.jamesyox.kastro.luna.calculateLunarState
import dev.jamesyox.kastro.luna.closestPhase
import java.time.Instant as JavaInstant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.math.roundToInt

@OptIn(ExperimentalTime::class)
class MoonRepositoryImpl(
    private val moonInfoMapper: MoonInfoMapper
) : MoonRepository {

    override suspend fun getMoonInfo(moonInfoRequest: MoonInfoRequest): MoonInfo {
        val (latitude, longitude) = moonInfoRequest.coordinates()
        val state = moonInfoRequest.date.atStartOfDayAsKastroInstant().calculateLunarState(latitude, longitude)

        return moonInfoMapper.map(
            MoonInfoDTO(
                date = moonInfoRequest.date,
                moonPhase = state.illumination.closestPhase.toLeuksnaPhase(),
                fraction = state.illumination.fraction.toFloat(),
                distance = state.position.distance,
                altitude = state.position.altitude.toRadian(),
                zodiac = moonInfoRequest.date.toZodiac(),
                moonRise = moonInfoRequest.findHorizonEvent(
                    latitude,
                    longitude,
                    LunarEvent.HorizonEvent.Moonrise
                ),
                moonSet = moonInfoRequest.findHorizonEvent(
                    latitude,
                    longitude,
                    LunarEvent.HorizonEvent.Moonset
                )
            )
        )
    }

    override suspend fun getMoonVisibility(moonInfoRequest: MoonInfoRequest): Int {
        val (latitude, longitude) = moonInfoRequest.coordinates()
        return (moonInfoRequest.date.atStartOfDayAsKastroInstant()
            .calculateLunarState(latitude, longitude)
            .illumination.fraction * 100).roundToInt()
    }

    private fun MoonInfoRequest.coordinates(): Pair<Double, Double> =
        (latitude ?: DEFAULT_LATITUDE) to (longitude ?: DEFAULT_LONGITUDE)

    private fun MoonInfoRequest.findHorizonEvent(
        latitude: Double,
        longitude: Double,
        event: LunarEvent.HorizonEvent.HorizonEventType
    ): LocalDateTime {
        val targetDate = date
        return LunarHorizonEventSequence(
            start = targetDate.atStartOfDayAsKastroInstant(),
            latitude = latitude,
            longitude = longitude,
            limit = 2.days,
            requestedHorizonEvents = listOf(event)
        ).map { it.time.toLocalDateTime() }
            .takeWhile { !it.toLocalDate().isAfter(targetDate) }
            .firstOrNull { it.toLocalDate() == targetDate }
            ?: targetDate.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime()
    }

    private fun LocalDate.atStartOfDayAsKastroInstant(): Instant {
        val javaInstant = atStartOfDay(ZoneId.systemDefault()).toInstant()
        return Instant.fromEpochSeconds(javaInstant.epochSecond, javaInstant.nano)
    }

    private fun Instant.toLocalDateTime(): LocalDateTime =
        JavaInstant.ofEpochSecond(epochSeconds, nanosecondsOfSecond.toLong())
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

    private fun LocalDate.toZodiac(): Zodiac = when (monthValue * 100 + dayOfMonth) {
        in 120..218 -> Zodiac.AQUARIUS
        in 219..320 -> Zodiac.PISCES
        in 321..419 -> Zodiac.ARIES
        in 420..520 -> Zodiac.TAURUS
        in 521..620 -> Zodiac.GEMINI
        in 621..722 -> Zodiac.CANCER
        in 723..822 -> Zodiac.LEO
        in 823..922 -> Zodiac.VIRGO
        in 923..1022 -> Zodiac.LIBRA
        in 1023..1121 -> Zodiac.SCORPIO
        in 1122..1221 -> Zodiac.SAGITTARIUS
        else -> Zodiac.CAPRICORN
    }

    private companion object {
        const val DEFAULT_LATITUDE = 40.416775
        const val DEFAULT_LONGITUDE = -3.703790
    }
}

internal fun LunarPhase.toLeuksnaPhase(): MoonPhase = when (this) {
    LunarEvent.PhaseEvent.NewMoon -> MoonPhase.NEW_MOON
    LunarPhase.Intermediate.WaxingCrescent -> MoonPhase.WAXING_CRESCENT
    LunarEvent.PhaseEvent.FirstQuarter -> MoonPhase.FIRST_QUARTER
    LunarPhase.Intermediate.WaxingGibbous -> MoonPhase.WAXING_GIBBOUS
    LunarEvent.PhaseEvent.FullMoon -> MoonPhase.FULL_MOON
    LunarPhase.Intermediate.WaningGibbous -> MoonPhase.WANING_GIBBOUS
    LunarEvent.PhaseEvent.LastQuarter -> MoonPhase.LAST_QUARTER
    LunarPhase.Intermediate.WaningCrescent -> MoonPhase.WANING_CRESCENT
}
