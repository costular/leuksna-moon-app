package com.costular.leuksna_moon_phases.presentation.settings

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.costular.leuksna_moon_phases.R
import com.costular.leuksna_moon_phases.domain.model.Location
import com.costular.leuksna_moon_phases.domain.model.MeasureUnit
import com.costular.leuksna_moon_phases.util.gone
import com.costular.leuksna_moon_phases.util.visible
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.snackbar.Snackbar
import io.uniflow.android.flow.onEvents
import io.uniflow.android.flow.onStates
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val settingsViewModel: SettingsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        bindActions()

        onStates(settingsViewModel) { state ->
            when (state) {
                is SettingsState -> handleState(state)
            }
        }
        onEvents(settingsViewModel) { event ->
            when (val data = event.take()) {
                is SettingsEvents.RetrieveLocationFailure -> {
                    showRetrieveLocationFailure(data.message)
                }
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
        bindUnitToggle()
        buttonSetLocation.setOnClickListener {
            setLocationWithPermissionCheck()
        }
        buttonClearLocation.setOnClickListener {
            settingsViewModel.clearLocation()
        }
    }

    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    fun setLocation() {
        settingsViewModel.retrieveLocation()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    private fun bindUnitToggle() {
        toggleUnit.addOnButtonCheckedListener(toggleUnitListener)
    }

    private fun unbindUnitToggle() {
        toggleUnit.removeOnButtonCheckedListener(toggleUnitListener)
    }

    private fun handleState(state: SettingsState) {
        unbindUnitToggle()
        when (state.measureUnit) {
            MeasureUnit.KM -> toggleUnit.check(R.id.buttonKm)
            MeasureUnit.MI -> toggleUnit.check(R.id.buttonMi)
        }
        bindUnitToggle()

        when (state.location) {
            is Location.NotSet -> {
                buttonSetLocation.visible()
                buttonClearLocation.gone()
                textLocation.setText(R.string.settings_no_location)
            }
            is Location.Set -> {
                buttonSetLocation.gone()
                buttonClearLocation.visible()
                textLocation.text = state.location.name
            }
        }
    }

    private fun showRetrieveLocationFailure(message: String) {
        Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show()
    }

    private val toggleUnitListener =
        MaterialButtonToggleGroup.OnButtonCheckedListener { view, _, _ ->
            val unit = when (view.checkedButtonId) {
                R.id.buttonMi -> MeasureUnit.MI
                else -> MeasureUnit.KM
            }
            settingsViewModel.setMeasureUnit(unit)
        }
}
