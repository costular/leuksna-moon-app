package com.costular.leuksna_moon_phases.di

import com.costular.leuksna_moon_phases.presentation.settings.SettingsHelper
import com.costular.leuksna_moon_phases.presentation.settings.SettingsHelperImpl
import com.costular.leuksna_moon_phases.util.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val app = module {

    single {
        MoonPhaseFormatter(androidContext().resources)
    }

    single {
        ZodiacFormatter(androidContext().resources)
    }

    single<LocaleHelper> {
        LocaleHelperImpl(androidContext().resources)
    }

    single<SettingsHelper> {
        SettingsHelperImpl(androidContext(), get())
    }

    single<LocationHelper> {
        LocationHelperImpl(androidContext(), get())
    }

    single<StringsHelper> {
        StringsHelperImpl(androidContext())
    }

}