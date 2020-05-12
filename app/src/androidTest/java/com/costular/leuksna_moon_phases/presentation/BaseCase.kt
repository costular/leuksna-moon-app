package com.costular.leuksna_moon_phases.presentation

import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase

abstract class BaseCase : TestCase(kaspressoBuilder = Kaspresso.Builder.simple())
