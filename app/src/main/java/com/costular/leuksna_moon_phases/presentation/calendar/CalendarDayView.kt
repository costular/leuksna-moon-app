package com.costular.leuksna_moon_phases.presentation.calendar

import android.view.View
import com.costular.leuksna_moon_phases.R
import com.costular.leuksna_moon_phases.presentation.view.ProgressTextView
import com.kizitonwose.calendar.view.ViewContainer

class CalendarDayView(view: View) : ViewContainer(view) {

    internal val progressView: ProgressTextView = view.findViewById(R.id.textProgressDay)
}
