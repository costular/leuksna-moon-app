package com.costular.leuksna_moon_phases.di

import com.costular.leuksna_moon_phases.data.MoonRepositoryImpl
import com.costular.leuksna_moon_phases.data.mapper.SunkalckMapper
import com.costular.leuksna_moon_phases.domain.MoonRepository
import com.costular.leuksna_moon_phases.domain.model.mapper.MoonInfoMapper
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

val data = module {

    single {
        MoonInfoMapper(get())
    }

    single {
        SunkalckMapper(get())
    }

    single<MoonRepository> {
        MoonRepositoryImpl(get())
    }

}
