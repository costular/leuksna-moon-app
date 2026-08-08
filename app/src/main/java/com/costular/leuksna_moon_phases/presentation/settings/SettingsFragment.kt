package com.costular.leuksna_moon_phases.presentation.settings

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.costular.leuksna_moon_phases.R
import com.costular.leuksna_moon_phases.databinding.FragmentSettingsBinding
import com.costular.leuksna_moon_phases.domain.model.Location
import com.costular.leuksna_moon_phases.domain.model.MeasureUnit
import com.costular.leuksna_moon_phases.util.gone
import com.costular.leuksna_moon_phases.util.visible
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.snackbar.Snackbar
import io.uniflow.android.livedata.onEvents
import io.uniflow.android.livedata.onStates
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val settingsViewModel: SettingsViewModel by viewModel()
    private val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) settingsViewModel.retrieveLocation()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingsBinding.bind(view)
        setUpToolbar()
        bindActions()

        onStates(settingsViewModel) { state ->
            when (state) {
                is SettingsState -> handleState(state)
            }
        }
        onEvents(settingsViewModel) { event ->
            when (val data = event) {
                is SettingsEvents.RetrieveLocationFailure -> {
                    showRetrieveLocationFailure(data.message)
                }
            }
        }

        settingsViewModel.load()
    }

    private fun setUpToolbar() {
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_close)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun bindActions() {
        bindUnitToggle()
        binding.buttonSetLocation.setOnClickListener {
            locationPermissionRequest.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        binding.buttonClearLocation.setOnClickListener {
            settingsViewModel.clearLocation()
        }
    }

    private fun bindUnitToggle() {
        binding.toggleUnit.addOnButtonCheckedListener(toggleUnitListener)
    }

    private fun unbindUnitToggle() {
        binding.toggleUnit.removeOnButtonCheckedListener(toggleUnitListener)
    }

    private fun handleState(state: SettingsState) {
        unbindUnitToggle()
        when (state.measureUnit) {
            MeasureUnit.KM -> binding.toggleUnit.check(R.id.buttonKm)
            MeasureUnit.MI -> binding.toggleUnit.check(R.id.buttonMi)
        }
        bindUnitToggle()

        when (state.location) {
            is Location.NotSet -> {
                binding.buttonSetLocation.visible()
                binding.buttonClearLocation.gone()
                binding.textLocation.setText(R.string.settings_no_location)
            }
            is Location.Set -> {
                binding.buttonSetLocation.gone()
                binding.buttonClearLocation.visible()
                binding.textLocation.text = state.location.name
            }
        }
    }

    private fun showRetrieveLocationFailure(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private val toggleUnitListener =
        MaterialButtonToggleGroup.OnButtonCheckedListener { view, _, _ ->
            val unit = when (view.checkedButtonId) {
                R.id.buttonMi -> MeasureUnit.MI
                else -> MeasureUnit.KM
            }
            settingsViewModel.setMeasureUnit(unit)
        }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
