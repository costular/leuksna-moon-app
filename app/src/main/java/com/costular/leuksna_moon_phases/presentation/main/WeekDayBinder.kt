package com.costular.leuksna_moon_phases.presentation.main

import android.view.View
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

typealias OnDateSelected = (newDate: LocalDate) -> Unit

class WeekDayBinder(
    var selectedDate: LocalDate,
    private val onDateSelected: OnDateSelected
) : DayBinder<WeekDayView> {

    private val dayFormatter = DateTimeFormatter.ofPattern("dd")
    private val monthFormatter = DateTimeFormatter.ofPattern("MMM")

    override fun bind(container: WeekDayView, day: CalendarDay) = with(container) {
        textDay.text = dayFormatter.format(day.date)
        textMonth.text = monthFormatter.format(day.date)

        view.isSelected = selectedDate == day.date
        view.setOnClickListener {
            onDateSelected(day.date)
        }
    }

    override fun create(view: View): WeekDayView = WeekDayView(view)

}