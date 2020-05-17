package com.costular.leuksna_moon_phases.domain.model.mapper

import com.costular.leuksna_moon_phases.data.model.MoonInfoDTO
import com.costular.leuksna_moon_phases.domain.model.MeasureUnit
import com.costular.leuksna_moon_phases.domain.model.MoonInfo
import com.costular.leuksna_moon_phases.presentation.settings.SettingsHelper
import com.costular.leuksna_moon_phases.util.UnitHelper
import com.costular.leuksna_moon_phases.util.toDegree
import kotlin.math.roundToInt

class MoonInfoMapper(private val settingsHelper: SettingsHelper) {

    fun map(input: MoonInfoDTO): MoonInfo =
        MoonInfo(
            input.date,
            input.moonPhase,
            "${((input.fraction * 100).roundToInt())}%",
            UnitHelper.calculateDistance(input.distance, getMeasureUnit()),
            UnitHelper.calculateAltitude(input.altitude),
            input.zodiac,
            input.moonRise,
            input.moonSet
        )

    private fun getMeasureUnit(): MeasureUnit = settingsHelper.getMeasureUnit()

}
