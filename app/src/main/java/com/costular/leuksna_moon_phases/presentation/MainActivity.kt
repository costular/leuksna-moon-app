package com.costular.leuksna_moon_phases.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.costular.leuksna_moon_phases.R
import com.costular.leuksna_moon_phases.presentation.main.MainViewModel
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
