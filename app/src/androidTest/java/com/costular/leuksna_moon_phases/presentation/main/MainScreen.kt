package com.costular.leuksna_moon_phases.presentation.main

import com.agoda.kakao.common.views.KView
import com.costular.leuksna_moon_phases.R
import com.kaspersky.kaspresso.screens.KScreen

object MainScreen : KScreen<MainScreen>() {

    override val layoutId: Int? = R.layout.fragment_main
    override val viewClass: Class<*>? = MainFragment::class.java

    val settingsButton = KView { withId(R.id.buttonSettings) }
    val calendarButton = KView { withId(R.id.buttonCalendar) }

}