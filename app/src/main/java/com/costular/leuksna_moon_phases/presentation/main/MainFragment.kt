package com.costular.leuksna_moon_phases.presentation.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.costular.leuksna_moon_phases.R
import io.uniflow.android.flow.onStates
import io.uniflow.core.flow.UIState
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class MainFragment : Fragment(R.layout.fragment_main) {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onStates(mainViewModel) { state ->
            when (state) {
                is MainViewState -> handleState(state)
                is UIState.Failed -> handleError(state.error)
            }
        }

        mainViewModel.getMoonInfo(LocalDate.now(), null, null)
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
    }

    private fun handleError(throwable: Throwable?) {
        
    }

}