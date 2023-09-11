package com.victor.composecalculator

import com.victor.composecalculator.model.UiEvent
import io.mockk.mockk
import org.junit.Assert.assertTrue
import org.junit.Test


class CalculatorViewModelTest {

    val viewModel = mockk<CalculatorViewModel>()

    @Test
    fun `Given user type a number, then currentNumber should be set`() {
        for (i in 1..10) {
            val num = (1..1000).random()
            viewModel.onEvent(UiEvent.TypeNumber(num))
            assertTrue(viewModel.currentNumber.value == num)
        }
    }
}