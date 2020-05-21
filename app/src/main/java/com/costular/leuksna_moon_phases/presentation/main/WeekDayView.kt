package com.costular.leuksna_moon_phases.presentation.main

import android.view.View
import android.widget.TextView
import com.costular.leuksna_moon_phases.R
import com.kizitonwose.calendarview.ui.ViewContainer

class WeekDayView(view: View) : ViewContainer(view) {

    internal val textMonth = view.findViewById<TextView>(R.id.textMonth)
    internal val textDay = view.findViewById<TextView>(R.id.textDay)

}