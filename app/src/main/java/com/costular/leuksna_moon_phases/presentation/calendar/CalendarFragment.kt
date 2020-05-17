package com.costular.leuksna_moon_phases.presentation.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.costular.leuksna_moon_phases.R
import com.costular.leuksna_moon_phases.di.calendar
import com.costular.leuksna_moon_phases.presentation.main.MainViewModel
import com.costular.leuksna_moon_phases.presentation.view.RoundedBottomSheetFragment
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.previous
import com.kizitonwose.calendarview.utils.yearMonth
import io.uniflow.android.flow.onStates
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.WeekFields
import java.util.Locale

class CalendarFragment : RoundedBottomSheetFragment() {

    val args: CalendarFragmentArgs by navArgs()

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val calendarViewModel: CalendarViewModel by viewModel()

    private val monthFormatter = DateTimeFormatter.ofPattern("MMMM")

    private lateinit var selectedDate: LocalDate

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readArgs()
        bindActions()
        initCalendar()

        onStates(calendarViewModel) { state ->
            when (state) {
                is CalendarState -> handleState(state)
            }
        }

        calendarViewModel.selectDate(selectedDate)
    }

    private fun readArgs() {
        selectedDate = LocalDate.parse(args.selectedDate)
    }

    private fun handleState(state: CalendarState) {
        printCalendar(state.selectedDate)
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
        textHeader.setOnClickListener {
            calendar.smoothScrollToMonth(YearMonth.now())
        }
    }

    private fun initCalendar() {
        with(calendar) {
            dayBinder =
                CalendarDayBinder(selectedDate, { mainViewModel.getDayProgress(it) }) { date ->
                    mainViewModel.selectDate(date)
                    calendarViewModel.selectDate(date)
                }
            val current = YearMonth.now()
            val selectedMonth = selectedDate.yearMonth

            val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
            setup(current.minusMonths(12), current.plusMonths(12), firstDayOfWeek)
            scrollToMonth(selectedMonth)

            monthScrollListener = { month ->
                textHeader.text = "${month.yearMonth.format(monthFormatter)} ${month.year}"
            }
        }
    }

    private fun printCalendar(selectedDate: LocalDate) {
        val oldDate = this.selectedDate
        this.selectedDate = selectedDate

        (calendar.dayBinder as CalendarDayBinder).selectedDate = selectedDate

        calendar.notifyDateChanged(oldDate)
        calendar.notifyDateChanged(selectedDate)
    }
}
