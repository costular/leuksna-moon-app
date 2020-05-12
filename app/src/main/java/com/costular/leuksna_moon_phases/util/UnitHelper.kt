package com.costular.leuksna_moon_phases.util

import com.costular.leuksna_moon_phases.domain.model.MeasureUnit
import java.util.*

object UnitHelper {

    fun findMeasureUnit(locale: Locale): MeasureUnit =
        when (locale.country.toUpperCase()) {
            "US", "GB", "MM", "LR" -> MeasureUnit.MI
            else -> MeasureUnit.KM
        }
}
