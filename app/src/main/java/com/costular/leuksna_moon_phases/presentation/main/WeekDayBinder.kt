package com.costular.leuksna_moon_phases.presentation.main

import android.view.View
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.view.WeekDayBinder as CalendarWeekDayBinder
import java.time.LocalDate
import java.time.format.DateTimeFormatter

typealias OnDateSelected = (newDate: LocalDate) -> Unit

class WeekDayBinder(
    var selectedDate: LocalDate,
    private val onDateSelected: OnDateSelected
) : CalendarWeekDayBinder<WeekDayView> {

    private val dayFormatter = DateTimeFormatter.ofPattern("dd")
    private val monthFormatter = DateTimeFormatter.ofPattern("MMM")

    override fun bind(container: WeekDayView, data: WeekDay) = with(container) {
        textDay.text = dayFormatter.format(data.date)
        textMonth.text = monthFormatter.format(data.date)

        view.isSelected = selectedDate == data.date
        view.isActivated = LocalDate.now() == data.date
        view.setOnClickListener {
            onDateSelected(data.date)
        }
    }

    override fun create(view: View): WeekDayView = WeekDayView(view)

}
