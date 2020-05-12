package com.costular.leuksna_moon_phases.util

import android.content.res.Resources
import com.costular.leuksna_moon_phases.R
import com.costular.leuksna_moon_phases.domain.model.Zodiac

class ZodiacFormatter(private val resources: Resources) {

    fun format(zodiac: Zodiac): String {
        val zodiacStringId = when (zodiac) {
            Zodiac.ARIES -> R.string.aries
            Zodiac.AQUARIUS -> R.string.aquarius
            Zodiac.CANCER -> R.string.cancer
            Zodiac.CAPRICORN -> R.string.capricorn
            Zodiac.GEMINI -> R.string.gemini
            Zodiac.LEO -> R.string.leo
            Zodiac.LIBRA -> R.string.libra
            Zodiac.PISCES -> R.string.pisces
            Zodiac.SAGITTARIUS -> R.string.sagittarius
            Zodiac.SCORPIO -> R.string.scorpio
            Zodiac.TAURUS -> R.string.taurus
            Zodiac.VIRGO -> R.string.virgo
        }
        return resources.getString(zodiacStringId)
    }
}
