package com.costular.leuksna_moon_phases.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.costular.leuksna_moon_phases.R
import com.costular.leuksna_moon_phases.presentation.main.MainViewModel
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_main)
        applySafeDrawingInsets(findViewById(R.id.nav_host_fragment))
    }

    private fun applySafeDrawingInsets(view: View) {
        val initialLeftPadding = view.paddingLeft
        val initialTopPadding = view.paddingTop
        val initialRightPadding = view.paddingRight
        val initialBottomPadding = view.paddingBottom

        ViewCompat.setOnApplyWindowInsetsListener(view) { insetView, windowInsets ->
            val safeDrawingInsets = windowInsets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )
            insetView.updatePadding(
                left = initialLeftPadding + safeDrawingInsets.left,
                top = initialTopPadding + safeDrawingInsets.top,
                right = initialRightPadding + safeDrawingInsets.right,
                bottom = initialBottomPadding + safeDrawingInsets.bottom
            )
            windowInsets
        }
        ViewCompat.requestApplyInsets(view)
    }
}
