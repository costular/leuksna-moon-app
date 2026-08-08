package com.costular.leuksna_moon_phases.di

import com.costular.leuksna_moon_phases.presentation.calendar.CalendarViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val calendar = module {

    viewModelOf(::CalendarViewModel)
}
