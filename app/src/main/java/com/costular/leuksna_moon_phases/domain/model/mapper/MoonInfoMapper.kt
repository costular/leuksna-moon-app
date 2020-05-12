package com.costular.leuksna_moon_phases.domain.model.mapper

import com.costular.leuksna_moon_phases.data.model.MoonInfoDTO
import com.costular.leuksna_moon_phases.domain.model.MoonInfo
import com.costular.leuksna_moon_phases.presentation.settings.SettingsHelper
import kotlin.math.roundToInt

class MoonInfoMapper(private val settingsHelper: SettingsHelper) {

    fun map(input: MoonInfoDTO): MoonInfo =
        MoonInfo(
            input.date,
            input.moonPhase,
            "${((input.fraction * 100).roundToInt())}%",
            "${input.distance}${printMeasureUnit()}",
            "${input.altitude}${printMeasureUnit()}",
            input.zodiac,
            input.moonRise,
            input.moonSet
        )

    private fun printMeasureUnit(): String = settingsHelper.getMeasureUnit().value
}
