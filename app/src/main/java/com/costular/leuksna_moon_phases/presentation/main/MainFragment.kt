package com.costular.leuksna_moon_phases.presentation.main

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.costular.leuksna_moon_phases.R
import com.costular.leuksna_moon_phases.databinding.FragmentMainBinding
import com.costular.leuksna_moon_phases.util.MoonPhaseFormatter
import com.costular.leuksna_moon_phases.util.ZodiacFormatter
import io.uniflow.android.livedata.onEvents
import io.uniflow.android.livedata.onStates
import io.uniflow.core.flow.data.UIState
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.WeekFields
import java.util.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class MainFragment : Fragment(R.layout.fragment_main) {

    private companion object {
        const val CALENDAR_CENTER_OFFSET_DAYS = 3L
        const val MOON_ROTATION_DURATION_MS = 90_000L
        const val FULL_ROTATION_DEGREES = 360f
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val mainViewModel: MainViewModel by activityViewModel()
    private val moonPhaseFormatter: MoonPhaseFormatter by inject()
    private val zodiacFormatter: ZodiacFormatter by inject()

    private var selectedDate: LocalDate = LocalDate.now()
    private var moonRotationAnimator: ObjectAnimator? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        onStates(mainViewModel) { state ->
            when (state) {
                is MainViewState -> handleState(state)
                is UIState.Failed -> handleError(state.error?.origin)
            }
        }
        onEvents(mainViewModel) { event ->
            when (val data = event) {
                is MainEvents.OpenCalendar -> openCalendar(data.selectedDate)
                is MainEvents.OpenSettings -> openSettings()
            }
        }

        bindActions()
        generateCalendar()
        mainViewModel.getMoonInfo()
    }

    private fun bindActions() {
        binding.buttonCalendar.setOnClickListener { mainViewModel.openCalendar() }
        binding.buttonSettings.setOnClickListener { mainViewModel.openSettings() }
        binding.textCurrentDate.setOnClickListener {
            binding.horizontalCalendar.scrollToDate(
                selectedDate.minusDays(CALENDAR_CENTER_OFFSET_DAYS)
            )
        }
    }

    override fun onStart() {
        super.onStart()
        binding.viewSky.onStart()
        startMoonRotation()
    }

    override fun onStop() {
        binding.viewSky.onStop()
        stopMoonRotation()
        super.onStop()
    }

    private fun startMoonRotation() {
        if (!ValueAnimator.areAnimatorsEnabled() || moonRotationAnimator != null) return

        moonRotationAnimator = ObjectAnimator.ofFloat(
            binding.imageMoon,
            View.ROTATION,
            binding.imageMoon.rotation,
            binding.imageMoon.rotation + FULL_ROTATION_DEGREES
        ).apply {
            duration = MOON_ROTATION_DURATION_MS
            interpolator = LinearInterpolator()
            start()
        }
    }

    private fun stopMoonRotation() {
        moonRotationAnimator?.cancel()
        moonRotationAnimator = null
    }

    private fun generateCalendar() {
        val start = YearMonth.now().minusMonths(6).atDay(1)
        val end = YearMonth.now().plusMonths(6).atEndOfMonth()
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek

        with(binding.horizontalCalendar) {
            dayViewResource = R.layout.item_week_day
            dayBinder = WeekDayBinder(selectedDate, ::onDateSelected)
            setup(start, end, firstDayOfWeek)
        }
    }

    private fun onDateSelected(newDate: LocalDate) {
        mainViewModel.selectDate(newDate)
    }

    private fun handleState(state: MainViewState) = with(state) {
        moonInfo?.let { moonInfo ->
            binding.textMoonriseTime.text = moonInfo.moonRise.format(
                DateTimeFormatter.ofLocalizedTime(
                    FormatStyle.SHORT
                )
            )

            binding.textMoonSetTime.text = moonInfo.moonSet.format(
                DateTimeFormatter.ofLocalizedTime(
                    FormatStyle.SHORT
                )
            )

            binding.imageMoon.setImageResource(moonPhaseFormatter.formatDrawableId(moonInfo.moonPhase))
            binding.textCurrentDate.text =
                date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
            binding.textMoonPhase.text = moonPhaseFormatter.formatName(moonInfo.moonPhase)

            binding.keyValueAltitude.value = moonInfo.altitude
            binding.keyValueDistance.value = moonInfo.distance
            binding.keyValueZodiac.value = zodiacFormatter.format(moonInfo.zodiac)
            binding.keyValueLuminosity.value = moonInfo.fraction
        }
        val oldDate = selectedDate
        selectedDate = state.date

        binding.horizontalCalendar.smoothScrollToDate(
            selectedDate.minusDays(CALENDAR_CENTER_OFFSET_DAYS)
        )

        (binding.horizontalCalendar.dayBinder as WeekDayBinder).selectedDate = selectedDate
        binding.horizontalCalendar.notifyDateChanged(oldDate)
        binding.horizontalCalendar.notifyDateChanged(selectedDate)
    }

    private fun handleError(throwable: Throwable?) {
    }

    private fun openCalendar(selectedDate: LocalDate) {
        findNavController().navigate(
            R.id.action_mainFragment_to_calendarFragment,
            bundleOf("selectedDate" to selectedDate.toString())
        )
    }

    private fun openSettings() {
        findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
    }

    override fun onDestroyView() {
        stopMoonRotation()
        _binding = null
        super.onDestroyView()
    }
}
