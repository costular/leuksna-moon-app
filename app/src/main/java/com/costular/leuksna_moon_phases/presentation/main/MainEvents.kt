package com.costular.leuksna_moon_phases.presentation.main

import io.uniflow.core.flow.UIEvent

sealed class MainEvents : UIEvent() {

    object OpenCalendar : MainEvents()

    object OpenSettings : MainEvents()

}