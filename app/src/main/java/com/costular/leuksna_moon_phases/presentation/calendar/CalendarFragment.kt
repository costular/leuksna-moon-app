package com.costular.leuksna_moon_phases.presentation.calendar

import android.os.Bundle
import android.view.View
import com.costular.leuksna_moon_phases.R
import com.costular.leuksna_moon_phases.databinding.FragmentCalendarBinding
import com.costular.leuksna_moon_phases.di.calendar
import com.costular.leuksna_moon_phases.presentation.main.MainViewModel
import com.costular.leuksna_moon_phases.presentation.view.RoundedBottomSheetFragment
import io.uniflow.android.livedata.onStates
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.Locale

class CalendarFragment : RoundedBottomSheetFragment(R.layout.fragment_calendar) {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val mainViewModel: MainViewModel by activityViewModel()
    private val calendarViewModel: CalendarViewModel by viewModel()

    private val monthFormatter = DateTimeFormatter.ofPattern("MMMM")

    private lateinit var selectedDate: LocalDate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCalendarBinding.bind(view)
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
        selectedDate = LocalDate.parse(requireArguments().getString("selectedDate"))
    }

    private fun handleState(state: CalendarState) {
        printCalendar(state.selectedDate)
    }

    private fun bindActions() {
        binding.buttonPrevMonth.setOnClickListener {
            binding.calendar.findFirstVisibleMonth()?.let {
                binding.calendar.smoothScrollToMonth(it.yearMonth.minusMonths(1))
            }
        }
        binding.buttonNextMonth.setOnClickListener {
            binding.calendar.findFirstVisibleMonth()?.let {
                binding.calendar.smoothScrollToMonth(it.yearMonth.plusMonths(1))
            }
        }
        binding.textHeader.setOnClickListener {
            binding.calendar.smoothScrollToMonth(YearMonth.now())
        }
    }

    private fun initCalendar() {
        with(binding.calendar) {
            dayViewResource = R.layout.item_calendar_day
            dayBinder =
                CalendarDayBinder(selectedDate, { mainViewModel.getDayProgress(it) }) { date ->
                    mainViewModel.selectDate(date)
                    calendarViewModel.selectDate(date)
                }
            val current = YearMonth.now()
            val selectedMonth = YearMonth.from(selectedDate)

            val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
            setup(current.minusMonths(12), current.plusMonths(12), firstDayOfWeek)
            scrollToMonth(selectedMonth)

            monthScrollListener = { month ->
                binding.textHeader.text = "${month.yearMonth.format(monthFormatter)} ${month.yearMonth.year}"
            }
        }
    }

    private fun printCalendar(selectedDate: LocalDate) {
        val oldDate = this.selectedDate
        this.selectedDate = selectedDate

        (binding.calendar.dayBinder as CalendarDayBinder).selectedDate = selectedDate

        binding.calendar.notifyDateChanged(oldDate)
        binding.calendar.notifyDateChanged(selectedDate)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
