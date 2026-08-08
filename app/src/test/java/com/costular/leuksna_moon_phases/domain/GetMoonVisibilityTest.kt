package com.costular.leuksna_moon_phases.domain

import com.costular.leuksna_moon_phases.domain.usecase.GetMoonVisibility
import com.costular.leuksna_moon_phases.presentation.CoroutineTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class GetMoonVisibilityTest : CoroutineTest() {

    lateinit var getMoonVisibility: GetMoonVisibility

    private val moonRepository: MoonRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        getMoonVisibility = GetMoonVisibility(moonRepository)
    }

    @Test
    fun `returns the repository moon visibility`() = runTest {
            // Given
            val expected = 100
            coEvery { moonRepository.getMoonVisibility(any()) } returns expected

            // When
            val actual = getMoonVisibility.execute(
                GetMoonVisibility.Params(
                    LocalDate.now(),
                    null,
                    null
                )
            )

            // Then
        assertEquals(expected, actual)
    }
}
