package com.costular.leuksna_moon_phases.di

import com.costular.leuksna_moon_phases.util.net.DefaultDispatcherFactory
import com.costular.leuksna_moon_phases.util.net.DispatcherFactory
import org.koin.dsl.module

val app = module {

    single<DispatcherFactory> {
        DefaultDispatcherFactory()
    }

}