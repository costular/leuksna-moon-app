package com.costular.leuksna_moon_phases.presentation.settings

import io.uniflow.core.flow.data.UIEvent

sealed class SettingsEvents : UIEvent() {

    data class RetrieveLocationFailure(val message: String) : SettingsEvents()
}
