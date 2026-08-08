package com.costular.leuksna_moon_phases.presentation.calendar

import android.view.View
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.view.MonthDayBinder
import java.time.LocalDate

class CalendarDayBinder(
    var selectedDate: LocalDate,
    private val progressGetter: (date: LocalDate) -> Int,
    private val listener: (date: LocalDate) -> Unit
) : MonthDayBinder<CalendarDayView> {

    override fun create(view: View): CalendarDayView = CalendarDayView(view)

    override fun bind(container: CalendarDayView, data: CalendarDay) {
        with(container.progressView) {
            updateContent(data.date.dayOfMonth.toString())
            updateProgress(progressGetter(data.date))

            val shouldBeSelected = data.date == selectedDate
            isSelected = shouldBeSelected
            isEnabled = data.position == DayPosition.MonthDate

            setOnClickListener {
                listener(data.date)
            }
        }
    }
}
