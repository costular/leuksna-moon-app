package com.costular.leuksna_moon_phases.presentation.calendar

import android.view.View
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder

class CalendarDayBinder : DayBinder<CalendarDayView> {

    override fun create(view: View): CalendarDayView = CalendarDayView(view)

    override fun bind(container: CalendarDayView, day: CalendarDay) {
        with (container.progressView) {
            updateContent(day.day.toString())
            updateProgress((0..100).random()) // TODO faked for now
        }
    }

}