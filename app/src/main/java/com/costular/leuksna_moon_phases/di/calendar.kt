package com.costular.leuksna_moon_phases.di

import com.costular.leuksna_moon_phases.presentation.calendar.CalendarViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val calendar = module {

    viewModel {
        CalendarViewModel()
    }
}
