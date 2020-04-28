package com.costular.leuksna_moon_phases.presentation.main

import android.Manifest
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import com.costular.leuksna_moon_phases.R
import com.costular.leuksna_moon_phases.presentation.BaseCase
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainFragmentTest : BaseCase() {

    @Test
    fun test() =
        run {
            step("Open settings") {
                // Given
                val navController = TestNavHostController(
                    ApplicationProvider.getApplicationContext())
                navController.setGraph(R.navigation.nav_graph)

                launchFragmentInContainer<MainFragment>(themeResId = R.style.AppTheme).onFragment { fragment ->
                    Navigation.setViewNavController(fragment.requireView(), navController)
                }

                // When
                MainScreen {
                    settingsButton {
                        click()
                    }
                }

                // Then
                Assert.assertEquals(R.id.settingsFragment, navController.currentDestination?.id)
            }

            step("Open calendar bottom sheet") {
                // Given
                val navController = TestNavHostController(
                    ApplicationProvider.getApplicationContext())
                navController.setGraph(R.navigation.nav_graph)

                launchFragmentInContainer<MainFragment>(themeResId = R.style.AppTheme).onFragment { fragment ->
                    Navigation.setViewNavController(fragment.requireView(), navController)
                }

                // When
                MainScreen {
                    calendarButton {
                        click()
                    }
                }

                // Then
                Assert.assertEquals(R.id.calendarFragment, navController.currentDestination?.id)
            }
        }

}