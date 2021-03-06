package com.costular.leuksna_moon_phases.di

import com.costular.leuksna_moon_phases.domain.usecase.GetMoonInfoUseCase
import com.costular.leuksna_moon_phases.domain.usecase.GetMoonVisibility
import com.costular.leuksna_moon_phases.presentation.main.MainInteractor
import com.costular.leuksna_moon_phases.presentation.main.MainInteractorImpl
import com.costular.leuksna_moon_phases.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

val main = module {

    single {
        GetMoonInfoUseCase(get())
    }

    single {
        GetMoonVisibility(get())
    }

    single<MainInteractor> {
        MainInteractorImpl(get(), get())
    }

    viewModel {
        MainViewModel(get(), get())
    }
}
