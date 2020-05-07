package com.costular.leuksna_moon_phases.presentation.settings

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.costular.leuksna_moon_phases.R
import com.costular.leuksna_moon_phases.domain.model.Location
import com.costular.leuksna_moon_phases.domain.model.MeasureUnit
import com.costular.leuksna_moon_phases.util.gone
import com.costular.leuksna_moon_phases.util.visible
import io.uniflow.android.flow.onStates
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    val settingsViewModel: SettingsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        bindActions()

        onStates(settingsViewModel) { state ->
            when (state) {
                is SettingsState -> handleState(state)
            }
        }

        settingsViewModel.load()
    }

    private fun setUpToolbar() {
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_close)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun bindActions() {
        toggleUnit.addOnButtonCheckedListener { view, _, _ ->
            val unit = when (view.checkedButtonId) {
                R.id.buttonMi -> MeasureUnit.MI
                else -> MeasureUnit.KM
            }
            settingsViewModel.setMeasureUnit(unit)
        }
    }

    private fun handleState(state: SettingsState) {
        when (state.measureUnit) {
            MeasureUnit.KM -> toggleUnit.check(R.id.buttonKm)
            MeasureUnit.MI -> toggleUnit.check(R.id.buttonMi)
        }

        when (state.location) {
            is Location.NotSet -> {
                buttonSetLocation.visible()
            }
            is Location.Set -> {
                buttonSetLocation.gone()
            }
        }
    }

}