package com.victor.composecalculator

import com.google.common.truth.Truth.assertThat
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
            viewModel = CalculatorViewModel()
            val num = (0..9).random()
            viewModel.onEvent(UiEvent.TypeNumber(num))
            assertThat(viewModel.currentNumber.value).isEqualTo(num.toString())
        }
    }

    @Test
    fun `Given a user type many numbers, the currentNumber should concatenate them`() {
        for (i in 1..5) {
            viewModel = CalculatorViewModel()
            val n = (1..5).random()
            var currentNumber = ""
            for (j in 0 until n) {
                val num = (0..9).random()
                viewModel.onEvent(UiEvent.TypeNumber(num))
                currentNumber += num
            }
            assertThat(viewModel.currentNumber.value).isEqualTo(currentNumber)
        }
    }

    @Test
    fun `Given user type 0 as first number, currentNumber should stay unchanged`() {
        val valueBefore = viewModel.currentNumber.value
        viewModel.onEvent(UiEvent.TypeNumber(0))

        assertThat(viewModel.currentNumber.value).isEqualTo(valueBefore)
    }

    @Test
    fun `Given user adds decimal character, then currentNumber should contain dot`() {
        for (i in 0 until 10) {
            viewModel = CalculatorViewModel()
            val num = (0..9).random()
            var currentNumber = "$num."
            viewModel.onEvent((UiEvent.TypeNumber(num)))
            viewModel.onEvent((UiEvent.AddDecimalCharacter))

            for (j in 0 until i) {
                val n = (0..9).random()
                currentNumber += n
                viewModel.onEvent((UiEvent.TypeNumber(n)))

            }
            assertThat(viewModel.currentNumber.value).isEqualTo(currentNumber)
        }
    }

    @Test
    fun `Given user adds decimal character more than once, they should not be added`() {
        for (i in 1..5) {
            viewModel = CalculatorViewModel()
            val num = (0..9).random()
            var currentNumber = "$num."
            viewModel.onEvent((UiEvent.TypeNumber(num)))
            viewModel.onEvent((UiEvent.AddDecimalCharacter))

            for (j in 0 until i) {
                viewModel.onEvent((UiEvent.AddDecimalCharacter))
                val n = (0..9).random()
                viewModel.onEvent((UiEvent.TypeNumber(n)))
                currentNumber += n
            }
            assertThat(viewModel.currentNumber.value).isEqualTo(currentNumber)
        }
    }
}