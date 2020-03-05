package com.costular.leuksna_moon_phases.domain

import com.costular.leuksna_moon_phases.domain.model.MoonInfo
import com.costular.leuksna_moon_phases.domain.model.MoonPhase
import com.costular.leuksna_moon_phases.domain.model.Zodiac
import com.costular.leuksna_moon_phases.domain.usecase.GetMoonInfoUseCase
import com.costular.leuksna_moon_phases.presentation.CoroutineTest
import io.kotlintest.TestCase
import io.kotlintest.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

@ExperimentalCoroutinesApi
class GetMoonInfoUseCaseTest : CoroutineTest() {

    lateinit var getMoonInfoUseCase: GetMoonInfoUseCase

    private val moonRepository: MoonRepository = mockk(relaxed = true)

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        getMoonInfoUseCase = GetMoonInfoUseCase(moonRepository)
    }

    init {
        "Given a value from repository when executing the usecase then should return the same value" {
            // Given
            val expected = MoonInfo(
                LocalDate.now(),
                MoonPhase.NEW_MOON,
                0,
                2420.0,
                3828383.0,
                Zodiac.PISCES,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(4)
            )
            coEvery { moonRepository.getMoonInfo(any()) } returns expected

            // When
            val actual = getMoonInfoUseCase.execute(GetMoonInfoUseCase.Params(LocalDate.now(), null, null))

            // Then
            actual.shouldBe(expected)
        }
    }

}