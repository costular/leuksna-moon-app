package com.costular.leuksna_moon_phases.presentation

import android.app.Application
import com.costular.leuksna_moon_phases.di.*
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MainApp)
            modules(listOf(
                app,
                data,
                main,
                calendar,
                settings
            ))
        }
    }

}