package com.costular.leuksna_moon_phases.di

import com.costular.leuksna_moon_phases.data.FakeMoonRepository
import com.costular.leuksna_moon_phases.domain.MoonRepository
import com.costular.leuksna_moon_phases.domain.model.mapper.MoonInfoMapper
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

val data = module {

    single<MoonRepository>(qualifier = StringQualifier("fake")) {
        FakeMoonRepository(get())
    }

    single {
        MoonInfoMapper(get())
    }
}
