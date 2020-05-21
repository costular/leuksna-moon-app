package com.costular.leuksna_moon_phases.presentation.main

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.costular.leuksna_moon_phases.R
import com.costular.leuksna_moon_phases.util.MoonPhaseFormatter
import com.costular.leuksna_moon_phases.util.ZodiacFormatter
import io.uniflow.android.flow.onEvents
import io.uniflow.android.flow.onStates
import io.uniflow.core.flow.data.UIState
import java.util.*
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import org.threeten.bp.temporal.WeekFields

class MainFragment : Fragment(R.layout.fragment_main) {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val moonPhaseFormatter: MoonPhaseFormatter by inject()
    private val zodiacFormatter: ZodiacFormatter by inject()

    private var selectedDate: LocalDate = LocalDate.now()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onStates(mainViewModel) { state ->
            when (state) {
                is MainViewState -> handleState(state)
                is UIState.Failed -> handleError(state.error)
            }
        }
        onEvents(mainViewModel) { event ->
            when (val data = event.take()) {
                is MainEvents.OpenCalendar -> openCalendar(data.selectedDate)
                is MainEvents.OpenSettings -> openSettings()
            }
        }

        bindActions()
        generateCalendar()
        mainViewModel.getMoonInfo()
    }

    private fun bindActions() {
        buttonCalendar.setOnClickListener { mainViewModel.openCalendar() }
        buttonSettings.setOnClickListener { mainViewModel.openSettings() }
        textCurrentDate.setOnClickListener {
            horizontalCalendar.scrollToDate(selectedDate.minusDays(2))
        }
    }

    override fun onStart() {
        super.onStart()
        viewSky.onStart()
    }

    override fun onStop() {
        viewSky.onStop()
        super.onStop()
    }

    private fun generateCalendar() {
        val start = YearMonth.now().minusMonths(6)
        val end = YearMonth.now().plusMonths(6)
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek

        val dm = DisplayMetrics()
        val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(dm)

        with(horizontalCalendar) {
            dayWidth = dm.widthPixels / 5
            dayHeight = (horizontalCalendar.dayWidth * 1.25).toInt()
            dayBinder = WeekDayBinder(selectedDate, ::onDateSelected)
            setup(start, end, firstDayOfWeek)
        }
    }

    private fun onDateSelected(newDate: LocalDate) {
        mainViewModel.selectDate(newDate)
    }

    private fun handleState(state: MainViewState) = with(state) {
        moonInfo?.let { moonInfo ->
            textMoonriseTime.text = moonInfo.moonRise.format(
                DateTimeFormatter.ofLocalizedTime(
                    FormatStyle.SHORT
                )
            )

            textMoonSetTime.text = moonInfo.moonSet.format(
                DateTimeFormatter.ofLocalizedTime(
                    FormatStyle.SHORT
                )
            )

            imageMoon.setImageResource(moonPhaseFormatter.formatDrawableId(moonInfo.moonPhase))
            textCurrentDate.text =
                date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
            textMoonPhase.text = moonPhaseFormatter.formatName(moonInfo.moonPhase)

            keyValueAltitude.value = moonInfo.altitude
            keyValueDistance.value = moonInfo.distance
            keyValueZodiac.value = zodiacFormatter.format(moonInfo.zodiac)
            keyValueLuminosity.value = moonInfo.fraction
        }
        val oldDate = selectedDate
        selectedDate = state.date

        horizontalCalendar.smoothScrollToDate(selectedDate.minusDays(2))

        (horizontalCalendar.dayBinder as WeekDayBinder).selectedDate = selectedDate
        horizontalCalendar.notifyDateChanged(oldDate)
        horizontalCalendar.notifyDateChanged(selectedDate)
    }

    private fun handleError(throwable: Throwable?) {
    }

    private fun openCalendar(selectedDate: LocalDate) {
        val action =
            MainFragmentDirections.actionMainFragmentToCalendarFragment(selectedDate.toString())
        findNavController().navigate(action)
    }

    private fun openSettings() {
        val action = MainFragmentDirections.actionMainFragmentToSettingsFragment()
        findNavController().navigate(action)
    }
}
