package com.costular.leuksna_moon_phases.util

import android.content.Context
import androidx.annotation.StringRes

interface StringsHelper {

    fun getString(@StringRes stringResId: Int): String

}

class StringsHelperImpl(private val context: Context) : StringsHelper {

    override fun getString(stringResId: Int): String =
        context.getString(stringResId)

}