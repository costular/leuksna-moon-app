package com.costular.leuksna_moon_phases.presentation.calendar

import android.view.View
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import org.threeten.bp.LocalDate

class CalendarDayBinder(
    var selectedDate: LocalDate,
    private val listener: (date: LocalDate) -> Unit
) : DayBinder<CalendarDayView> {

    override fun create(view: View): CalendarDayView = CalendarDayView(view)

    override fun bind(container: CalendarDayView, day: CalendarDay) {
        with (container.progressView) {
            updateContent(day.day.toString())
            updateProgress((0..100).random()) // TODO faked for now

            isSelected = day.date == selectedDate
            isEnabled = day.owner == DayOwner.THIS_MONTH

            setOnClickListener {
                listener(day.date)
            }
        }
    }

}