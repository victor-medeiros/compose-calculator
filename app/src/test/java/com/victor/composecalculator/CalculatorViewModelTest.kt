package com.victor.composecalculator

import com.victor.composecalculator.model.UiEvent
import org.junit.Before
import org.junit.Test


class CalculatorViewModelTest {

    private lateinit var viewModel: CalculatorViewModel

    @Before
    fun setup() {
        viewModel = CalculatorViewModel()
    }

    @Test
    fun `Given user type a number, then currentNumber should be set to it`() {
        for (i in 1..10) {
            val num = (0..9).random()
            viewModel.onEvent(UiEvent.TypeNumber(num))
            assert(viewModel.currentNumber.value == num.toString())
        }
    }

    @Test
    fun `Given a user type many numbers, the currentNumber should concatenate them` () {
        for (i in 1..5) {
            val n = (1..5).random()
            var currentNumber = ""
            for (j in 0 until n) {
                val num = (0..9).random()
                viewModel.onEvent(UiEvent.TypeNumber(num))
                currentNumber += num
            }
            assert(viewModel.currentNumber.value == currentNumber)
        }
    }
}