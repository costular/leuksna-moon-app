package com.costular.leuksna_moon_phases.util

import android.content.res.Resources
import android.os.Build
import java.util.*

interface LocaleHelper {

    fun getLocale(): Locale
}

class LocaleHelperImpl(private val resources: Resources) : LocaleHelper {

    override fun getLocale(): Locale =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resources.configuration.locales.get(0)
        } else {
            resources.configuration.locale
        }
}
