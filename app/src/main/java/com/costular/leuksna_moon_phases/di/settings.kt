package com.costular.leuksna_moon_phases.di

import com.costular.leuksna_moon_phases.presentation.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settings = module {

    viewModel {
        SettingsViewModel(get(), get(), get())
    }
}
