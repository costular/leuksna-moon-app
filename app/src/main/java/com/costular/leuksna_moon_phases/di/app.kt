package com.costular.leuksna_moon_phases.di

import com.costular.leuksna_moon_phases.util.MoonPhaseFormatter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val app = module {

    single {
        MoonPhaseFormatter(androidContext().resources)
    }

}