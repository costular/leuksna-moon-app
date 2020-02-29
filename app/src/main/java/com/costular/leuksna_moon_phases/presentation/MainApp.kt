package com.costular.leuksna_moon_phases.presentation

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

}