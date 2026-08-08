package com.costular.leuksna_moon_phases.util

import android.content.res.Resources
import java.util.*

interface LocaleHelper {

    fun getLocale(): Locale
}

class LocaleHelperImpl(private val resources: Resources) : LocaleHelper {

    override fun getLocale(): Locale = resources.configuration.locales[0]
}
