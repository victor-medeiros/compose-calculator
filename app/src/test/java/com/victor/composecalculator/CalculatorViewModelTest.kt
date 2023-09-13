package com.victor.composecalculator

import com.google.common.truth.Truth.assertThat
import com.victor.composecalculator.model.Operation
import com.victor.composecalculator.model.UiEvent
import com.victor.composecalculator.model.extension.addDecimal
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
        for (i in 0..9) {
            viewModel = CalculatorViewModel()
            viewModel.onEvent(UiEvent.TypeNumber(i))
            assertThat(viewModel.currentNumber.value).isEqualTo(i.toString())
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
        for (i in 0..9) {
            viewModel = CalculatorViewModel()
            var currentNumber = "$i."
            viewModel.onEvent((UiEvent.TypeNumber(i)))
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

    @Test
    fun `Given onEvent receives SUM event, then result should be the sum of the numbers typed`() {
        chainedOperation(Operation.SUM) { acc, n -> acc + n }
    }

    @Test
    fun `Given onEvent receives SUBTRACTION event, then result should be the subtraction of the numbers typed`() {
        chainedOperation(Operation.SUBTRACTION) { acc, n -> acc - n }
    }

    @Test
    fun `Given onEvent receives MULTIPLICATION event, then result should be the multiplication of the numbers typed`() {
        chainedOperation(Operation.MULTIPLICATION) { acc, n -> acc * n }
    }

    @Test
    fun `Given onEvent receives DIVISION event, then result should be the division of the numbers typed`() {
        chainedOperation(Operation.DIVISION) { acc, n -> acc / n }
    }

    private fun chainedOperation(operation: Operation, reduce: (Double, Double) -> Double) {
        for (i in 1..5) {
            val numbers = mutableListOf<Double>()
            val operations = mutableListOf<String>()

            val num = (1..100).random()
            viewModel.onEvent((UiEvent.TypeNumber(num)))
            numbers.add(num.toDouble())

            for (j in 0 until i) {
                viewModel.onEvent(UiEvent.AddOperation(operation))
                operations.add(operation.symbol)

                // to have some variation of decimal and integer numbers
                if (j % 2 == 0) {
                    val n = (1..100).random()
                    viewModel.onEvent((UiEvent.TypeNumber(n)))
                    numbers.add(n.toDouble())
                } else {
                    numbers.add(viewModel.addDecimal())
                }
            }
            viewModel.onEvent(UiEvent.CalculateOperation)

            assertThat(viewModel.numbers.value).isEqualTo(numbers)
            assertThat(viewModel.operations.value).isEqualTo(operations)
            assertThat(viewModel.result.value).isEqualTo(numbers.reduce(reduce))
        }
    }
}
