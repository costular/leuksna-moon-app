package com.costular.leuksna_moon_phases.domain

import com.costular.leuksna_moon_phases.domain.usecase.GetMoonVisibility
import com.costular.leuksna_moon_phases.presentation.CoroutineTest
import io.kotlintest.TestCase
import io.kotlintest.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.threeten.bp.LocalDate

@ExperimentalCoroutinesApi
class GetMoonVisibilityTest : CoroutineTest() {

    lateinit var getMoonVisibility: GetMoonVisibility

    private val moonRepository: MoonRepository = mockk(relaxed = true)

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        getMoonVisibility = GetMoonVisibility(moonRepository)
    }

    init {
        "Given a 100 visibility is returned by repository when executing usecase then it should return the same" {
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
            actual.shouldBe(expected)
        }
    }

}