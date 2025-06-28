package com.victor.composecalculator.ui

import com.google.common.truth.Truth.assertThat
import com.victor.composecalculator.model.Operation
import com.victor.composecalculator.model.UiEvent
import com.victor.composecalculator.model.extension.addDecimal
import com.victor.composecalculator.model.extension.addRandomCalculation
import com.victor.composecalculator.ui.calculator.CalculatorUiState
import com.victor.composecalculator.ui.calculator.CalculatorViewModel
import org.junit.Before
import org.junit.Test


class CalculatorViewModelTest {

    private lateinit var viewModel: CalculatorViewModel

    @Before
    fun setup() {
        viewModel = CalculatorViewModel()
    }

    @Test
    fun `Given user type a number, then expression should be set to it`() {
        for (i in 0..9) {
            viewModel = CalculatorViewModel()
            viewModel.onEvent(UiEvent.TypeNumber(i))
            assertThat(viewModel.calculatorUiState.value.expression).isEqualTo(i.toString())
        }
    }

    @Test
    fun `Given a user type many numbers, the currentNumber should concatenate them`() {
        for (i in 1..5) {
            viewModel = CalculatorViewModel()
            val n = (1..5).random()
            var currentNumber = ""
            for (j in 0 until n) {
                val num = if (j == 0 ) (1..9).random() else (0..9).random()
                viewModel.onEvent(UiEvent.TypeNumber(num))
                currentNumber += num
            }
            assertThat(viewModel.calculatorUiState.value.expression).isEqualTo(currentNumber)
        }
    }

    @Test
    fun `Given user type left 0 without decimal character, they should be ignored`() {
        for (i in 0..2) {
            viewModel = CalculatorViewModel()
            for (j in 0 until i) {
                viewModel.onEvent(UiEvent.TypeNumber(0))
            }
            viewModel.onEvent(UiEvent.TypeNumber(i))
            assertThat(viewModel.calculatorUiState.value.expression).isEqualTo(i.toString())
        }
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
            assertThat(viewModel.calculatorUiState.value.expression).isEqualTo(currentNumber)
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
            assertThat(viewModel.calculatorUiState.value.expression).isEqualTo(currentNumber)
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

    @Test
    fun `Given user adds an operation, then expression should contain it`() {
        for (operation in Operation.entries) {
            viewModel.onEvent(UiEvent.TypeNumber((0..9).random()))
            viewModel.onEvent(UiEvent.AddOperation(operation))
            val expected = if (operation == Operation.SUBTRACTION) "-" else ""
            assertThat(viewModel.calculatorUiState.value.expression).contains(expected)
        }
    }

    @Test
    fun `Giver user onEvent receives ClearExpression, the all the state should be cleared and restarted`() {
        viewModel.onEvent(UiEvent.TypeNumber((1..9).random()))
        viewModel.onEvent(UiEvent.AddOperation(Operation.entries.random()))
        viewModel.onEvent(UiEvent.TypeNumber((1..9).random()))
        viewModel.onEvent(UiEvent.CalculateOperation)
        viewModel.onEvent(UiEvent.TypeNumber((1..9).random()))
        viewModel.onEvent(UiEvent.AddOperation(Operation.entries.random()))
        viewModel.onEvent(UiEvent.ClearExpression)
        assertThat(viewModel.calculatorUiState.value).isEqualTo(CalculatorUiState())
    }

    @Test
    fun `Given user adds a number after calculation, then expression and result should be cleaned`() {
        viewModel.addRandomCalculation()

        val num = (0..9).random()
        viewModel.onEvent(UiEvent.TypeNumber(num))
        assertThat(viewModel.calculatorUiState.value).isEqualTo(
            CalculatorUiState(expression = num.toString())
        )
    }

    @Test
    fun `Given user adds an operation after calculation, then the previous result should be considered the first number`() {
        viewModel.addRandomCalculation()
        val result = viewModel.calculatorUiState.value.result
        val operation = Operation.entries.random()
        val num = (0..9).random()
        viewModel.onEvent(UiEvent.AddOperation(operation))
        viewModel.onEvent(UiEvent.TypeNumber(num))

        val expression = "$result${operation.symbol}$num"
        assertThat(viewModel.calculatorUiState.value).isEqualTo(
            CalculatorUiState(expression = expression)
        )
    }

    @Test
    fun `Given user adds operation after another operation, then the last should replace the previous`() {
        val num = (1..9).random()
        val operation = Operation.entries.random()

        viewModel.onEvent(UiEvent.TypeNumber(num))
        viewModel.onEvent(UiEvent.AddOperation(Operation.entries.random()))
        viewModel.onEvent(UiEvent.AddOperation(operation))

        val expression = "$num${operation.symbol}"
        assertThat(viewModel.calculatorUiState.value).isEqualTo(
            CalculatorUiState(expression = expression)
        )
    }

    @Test
    fun `Given user adds an operation without adding number, the event should be ignored`() {
        viewModel.onEvent(UiEvent.AddOperation(Operation.entries.random()))
        assertThat(viewModel.calculatorUiState.value).isEqualTo(CalculatorUiState())
    }

    private fun chainedOperation(operation: Operation, reduce: (Double, Double) -> Double) {
        for (i in 1..5) {
            val numbers = mutableListOf<Double>()

            val num = (1..100).random()
            viewModel.onEvent((UiEvent.TypeNumber(num)))
            numbers.add(num.toDouble())

            for (j in 0 until i) {
                viewModel.onEvent(UiEvent.AddOperation(operation))

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

            assertThat(viewModel.calculatorUiState.value.result).isEqualTo(numbers.reduce(reduce))
        }
    }
}
