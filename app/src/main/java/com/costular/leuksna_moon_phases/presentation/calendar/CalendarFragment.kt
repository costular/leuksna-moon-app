package com.costular.leuksna_moon_phases.presentation.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.costular.leuksna_moon_phases.R
import com.costular.leuksna_moon_phases.presentation.main.MainViewModel
import com.costular.leuksna_moon_phases.presentation.view.RoundedBottomSheetFragment
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.previous
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.WeekFields
import java.util.*

class CalendarFragment : RoundedBottomSheetFragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()

    private val monthFormatter = DateTimeFormatter.ofPattern("MMMM")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindActions()
        initCalendar()
    }

    private fun bindActions() {
        buttonPrevMonth.setOnClickListener {
            calendar.findFirstVisibleMonth()?.let {
                calendar.smoothScrollToMonth(it.yearMonth.previous)
            }
        }
        buttonNextMonth.setOnClickListener {
            calendar.findFirstVisibleMonth()?.let {
                calendar.smoothScrollToMonth(it.yearMonth.next)
            }
        }
    }

    private fun initCalendar() {
        with(calendar) {
            dayBinder = CalendarDayBinder()
            val current = YearMonth.now()
            val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
            setup(current.minusMonths(12), current.plusMonths(12), firstDayOfWeek)
            scrollToMonth(current)

            monthScrollListener = { month ->
                textHeader.text = "${month.yearMonth.format(monthFormatter)} ${month.year}"
            }
        }
    }

}