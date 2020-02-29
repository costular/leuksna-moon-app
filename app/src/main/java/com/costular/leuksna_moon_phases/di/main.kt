package com.costular.leuksna_moon_phases.di

import com.costular.leuksna_moon_phases.domain.interactor.GetMoonInfoInteractor
import com.costular.leuksna_moon_phases.presentation.main.MainInteractor
import com.costular.leuksna_moon_phases.presentation.main.MainInteractorImpl
import com.costular.leuksna_moon_phases.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val main = module {

    single {
        GetMoonInfoInteractor(get())
    }

    single<MainInteractor> {
        MainInteractorImpl(get())
    }

    viewModel {
        MainViewModel(get())
    }
}