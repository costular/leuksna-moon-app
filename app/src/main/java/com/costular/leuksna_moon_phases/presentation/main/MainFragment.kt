package com.costular.leuksna_moon_phases.presentation.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.costular.leuksna_moon_phases.R
import com.costular.leuksna_moon_phases.util.MoonPhaseFormatter
import com.costular.leuksna_moon_phases.util.toCalendar
import com.costular.leuksna_moon_phases.util.toLocalDate
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.HorizontalCalendarView
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import io.uniflow.android.flow.onStates
import io.uniflow.core.flow.UIState
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import java.util.*


class MainFragment : Fragment(R.layout.fragment_main) {

    private val mainViewModel: MainViewModel by viewModel()
    private val moonPhaseFormatter: MoonPhaseFormatter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onStates(mainViewModel) { state ->
            when (state) {
                is MainViewState -> handleState(state)
                is UIState.Failed -> handleError(state.error)
            }
        }

        generateCalendar()
        mainViewModel.getMoonInfo(LocalDate.now(), null, null)
    }

    private fun generateCalendar() {
        val start = LocalDate.now().minusMonths(1).toCalendar()
        val end = LocalDate.now().plusMonths(1).toCalendar()

        HorizontalCalendar.Builder(view, R.id.horizontalCalendar)
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

        textCurrentDate.text = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
        textMoonPhase.text = moonPhaseFormatter.format(moonInfo.moonPhase)
    }

    private fun handleError(throwable: Throwable?) {

    }

}