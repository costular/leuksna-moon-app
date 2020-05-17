package com.costular.leuksna_moon_phases.presentation.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.costular.leuksna_moon_phases.R
import com.costular.leuksna_moon_phases.util.MoonPhaseFormatter
import com.costular.leuksna_moon_phases.util.ZodiacFormatter
import com.costular.leuksna_moon_phases.util.toCalendar
import com.costular.leuksna_moon_phases.util.toLocalDate
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import io.uniflow.android.flow.onEvents
import io.uniflow.android.flow.onStates
import io.uniflow.core.flow.data.UIState
import java.util.*
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class MainFragment : Fragment(R.layout.fragment_main) {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val moonPhaseFormatter: MoonPhaseFormatter by inject()
    private val zodiacFormatter: ZodiacFormatter by inject()

    private lateinit var horizontalCalendarConfig: HorizontalCalendar

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
    }

    private fun bindActions() {
        buttonCalendar.setOnClickListener { mainViewModel.openCalendar() }
        buttonSettings.setOnClickListener { mainViewModel.openSettings() }
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
        val start = LocalDate.now().minusMonths(1).toCalendar()
        val end = LocalDate.now().plusMonths(1).toCalendar()

        horizontalCalendarConfig = HorizontalCalendar.Builder(view, R.id.horizontalCalendar)
            .range(start, end)
            .datesNumberOnScreen(5)
            .build()
            .apply {
                calendarListener = object : HorizontalCalendarListener() {
                    override fun onDateSelected(date: Calendar?, position: Int) {
                        date?.let { date ->
                            mainViewModel.selectDate(date.time.toLocalDate())
                        }
                    }
                }
            }
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
            textCurrentDate.text = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
            textMoonPhase.text = moonPhaseFormatter.formatName(moonInfo.moonPhase)

            keyValueAltitude.value = moonInfo.altitude
            keyValueDistance.value = moonInfo.distance
            keyValueZodiac.value = zodiacFormatter.format(moonInfo.zodiac)
            keyValueLuminosity.value = moonInfo.fraction
        }

        if (state.date != horizontalCalendarConfig.selectedDate.time.toLocalDate()) {
            horizontalCalendarConfig.selectDate(state.date.toCalendar(), true)
        }
    }

    private fun handleError(throwable: Throwable?) {
    }

    private fun openCalendar(selectedDate: LocalDate) {
        val action = MainFragmentDirections.actionMainFragmentToCalendarFragment(selectedDate.toString())
        findNavController().navigate(action)
    }

    private fun openSettings() {
        val action = MainFragmentDirections.actionMainFragmentToSettingsFragment()
        findNavController().navigate(action)
    }
}
