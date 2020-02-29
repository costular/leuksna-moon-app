package com.costular.leuksna_moon_phases.util

import android.content.res.Resources
import com.costular.leuksna_moon_phases.R
import com.costular.leuksna_moon_phases.domain.model.MoonPhase

class MoonPhaseFormatter(private val resources: Resources) {

    fun format(moonPhase: MoonPhase): String {
        val stringId = when (moonPhase) {
            MoonPhase.FULL_MOON -> R.string.full_moon
            MoonPhase.FIRST_QUARTER -> R.string.first_quarter
            MoonPhase.LAST_QUARTER -> R.string.last_quarter
            MoonPhase.NEW_MOON -> R.string.new_moon
            MoonPhase.WANING_CRESCENT -> R.string.waning_crescent
            MoonPhase.WANING_GIBBOUS -> R.string.waxing_gibbous
            MoonPhase.WAXING_CRESCENT -> R.string.waxing_crescent
            MoonPhase.WAXING_GIBBOUS -> R.string.waxing_gibbous
        }
        return resources.getString(stringId)
    }


}