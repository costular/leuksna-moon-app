package com.costular.leuksna_moon_phases.domain

import com.costular.leuksna_moon_phases.domain.model.MoonInfo
import com.costular.leuksna_moon_phases.domain.model.MoonPhase
import com.costular.leuksna_moon_phases.domain.model.Zodiac
import com.costular.leuksna_moon_phases.domain.usecase.GetMoonInfoUseCase
import com.costular.leuksna_moon_phases.presentation.CoroutineTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime

class GetMoonInfoUseCaseTest : CoroutineTest() {

    lateinit var getMoonInfoUseCase: GetMoonInfoUseCase

    private val moonRepository: MoonRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        getMoonInfoUseCase = GetMoonInfoUseCase(moonRepository)
    }

    @Test
    fun `returns the repository moon information`() = runTest {
            // Given
            val expected = MoonInfo(
                LocalDate.now(),
                MoonPhase.NEW_MOON,
                "0%",
                "2420.0",
                "3828383.0",
                Zodiac.PISCES,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(4)
            )
            coEvery { moonRepository.getMoonInfo(any()) } returns expected

            // When
            val actual =
                getMoonInfoUseCase.execute(GetMoonInfoUseCase.Params(LocalDate.now(), null, null))

            // Then
        assertEquals(expected, actual)
    }
}
