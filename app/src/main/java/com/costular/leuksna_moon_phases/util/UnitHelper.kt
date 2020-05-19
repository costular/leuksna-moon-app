package com.costular.leuksna_moon_phases.util

import com.costular.leuksna_moon_phases.domain.model.MeasureUnit
import java.text.DecimalFormat
import java.util.*
import kotlin.math.round

typealias Degree = Double
typealias Radian = Double

fun Degree.toRadian(): Radian = Math.toRadians(this)
fun Radian.toDegree(): Degree = Math.toDegrees(this)

object UnitHelper {

    private val decimalFormat by lazy(LazyThreadSafetyMode.NONE) {
        DecimalFormat("#.##")
    }

    fun findMeasureUnit(locale: Locale): MeasureUnit =
        when (locale.country.toUpperCase()) {
            "US", "GB", "MM", "LR" -> MeasureUnit.MI
            else -> MeasureUnit.KM
        }

    fun calculateAltitude(radians: Radian): String =
        "${decimalFormat.format(radians.toDegree())}º"

    fun calculateDistance(kilometer: Double, measureUnit: MeasureUnit): String {
        val value = when (measureUnit) {
            MeasureUnit.KM -> decimalFormat.format(kilometer)
            MeasureUnit.MI -> decimalFormat.format((kilometer * 0.621371))
        }
        return "$value${measureUnit.value}"
    }

}
