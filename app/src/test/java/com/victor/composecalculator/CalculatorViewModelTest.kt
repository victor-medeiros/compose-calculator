package com.victor.composecalculator

import com.google.common.truth.Truth.assertThat
import com.victor.composecalculator.model.Operation
import com.victor.composecalculator.model.UiEvent
import org.junit.Before
import org.junit.Test
import kotlin.random.Random


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
            viewModel.onEvent(UiEvent.TypeNumber(num.toDouble()))
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
                viewModel.onEvent(UiEvent.TypeNumber(num.toDouble()))
                currentNumber += num
            }
            assertThat(viewModel.currentNumber.value).isEqualTo(currentNumber)
        }
    }

    @Test
    fun `Given user type 0 as first number, currentNumber should stay unchanged`() {
        val valueBefore = viewModel.currentNumber.value
        viewModel.onEvent(UiEvent.TypeNumber(0.0))

        assertThat(viewModel.currentNumber.value).isEqualTo(valueBefore)
    }

    @Test
    fun `Given user adds decimal character, then currentNumber should contain dot`() {
        for (i in 0 until 10) {
            viewModel = CalculatorViewModel()
            val num = (0..9).random()
            var currentNumber = "$num."
            viewModel.onEvent((UiEvent.TypeNumber(num.toDouble())))
            viewModel.onEvent((UiEvent.AddDecimalCharacter))

            for (j in 0 until i) {
                val n = (0..9).random()
                currentNumber += n
                viewModel.onEvent((UiEvent.TypeNumber(n.toDouble())))

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
            viewModel.onEvent((UiEvent.TypeNumber(num.toDouble())))
            viewModel.onEvent((UiEvent.AddDecimalCharacter))

            for (j in 0 until i) {
                viewModel.onEvent((UiEvent.AddDecimalCharacter))
                val n = (0..9).random()
                viewModel.onEvent((UiEvent.TypeNumber(n.toDouble())))
                currentNumber += n
            }
            assertThat(viewModel.currentNumber.value).isEqualTo(currentNumber)
        }
    }

    @Test
    fun `Given onEvent receives SUM event, then result should be the sum of the numbers typed`() {
        for (i in 1..10) {
            val numbers = mutableListOf<Double>()
            val operations = mutableListOf<String>()

            val num = Random.nextDouble(0.0, 1000.0)
            viewModel.onEvent((UiEvent.TypeNumber(num)))
            numbers.add(num)

            for (j in 0 until i) {
                viewModel.onEvent(UiEvent.AddOperation(Operation.SUM))
                operations.add(Operation.MULTIPLICATION.symbol)

                val n = Random.nextDouble(0.0, 1000.0)
                viewModel.onEvent((UiEvent.TypeNumber(n)))
                numbers.add(n)
            }
            viewModel.onEvent(UiEvent.CalculateOperation)

            assertThat(viewModel.numbers.value).isEqualTo(numbers)
            assertThat(viewModel.operations.value).isEqualTo(operations)
            assertThat(viewModel.result.value).isEqualTo(numbers.sum())
        }
    }

    @Test
    fun `Given onEvent receives SUBTRACTION event, then result should be the subtraction of the numbers typed`() {
        for (i in 1..10) {
            val numbers = mutableListOf<Double>()
            val operations = mutableListOf<String>()

            val num = Random.nextDouble(0.0, 1000.0)
            viewModel.onEvent((UiEvent.TypeNumber(num)))
            numbers.add(num)

            for (j in 0 until i) {
                viewModel.onEvent(UiEvent.AddOperation(Operation.SUBTRACTION))
                operations.add(Operation.MULTIPLICATION.symbol)

                val n = Random.nextDouble(0.0, 1000.0)
                viewModel.onEvent((UiEvent.TypeNumber(n)))
                numbers.add(n)
            }
            viewModel.onEvent(UiEvent.CalculateOperation)
            assertThat(viewModel.numbers.value).isEqualTo(numbers)
            assertThat(viewModel.operations.value).isEqualTo(operations)
            assertThat(viewModel.result.value).isEqualTo(numbers.reduce { acc, d -> acc - d })
        }
    }

    @Test
    fun `Given onEvent receives MULTIPLICATION event, then result should be the multiplication of the numbers typed`() {
        for (i in 1..5) {
            val numbers = mutableListOf<Double>()
            val operations = mutableListOf<String>()

            val num = Random.nextDouble(0.0, 10.0)
            viewModel.onEvent((UiEvent.TypeNumber(num)))
            numbers.add(num)

            for (j in 0 until i) {
                viewModel.onEvent(UiEvent.AddOperation(Operation.MULTIPLICATION))
                operations.add(Operation.MULTIPLICATION.symbol)

                val n = Random.nextDouble(0.0, 10.0)
                viewModel.onEvent((UiEvent.TypeNumber(n)))
                numbers.add(n)
            }
            viewModel.onEvent(UiEvent.CalculateOperation)

            assertThat(viewModel.numbers.value).isEqualTo(numbers)
            assertThat(viewModel.operations.value).isEqualTo(operations)
            assertThat(viewModel.result.value).isEqualTo(numbers.reduce { acc, d -> acc * d })
        }
    }

    @Test
    fun `Given onEvent receives DIVISION event, then result should be the division of the numbers typed`() {
        for (i in 1..5) {
            val numbers = mutableListOf<Double>()
            val operations = mutableListOf<String>()

            val num = Random.nextDouble(0.0, 10.0)
            viewModel.onEvent((UiEvent.TypeNumber(num)))
            numbers.add(num)

            for (j in 0 until i) {
                viewModel.onEvent(UiEvent.AddOperation(Operation.DIVISION))
                operations.add(Operation.MULTIPLICATION.symbol)

                val n = Random.nextDouble(0.0, 10.0)
                viewModel.onEvent((UiEvent.TypeNumber(n)))
                numbers.add(n)
            }
            viewModel.onEvent(UiEvent.CalculateOperation)

            assertThat(viewModel.numbers.value).isEqualTo(numbers)
            assertThat(viewModel.operations.value).isEqualTo(operations)
            assertThat(viewModel.result.value).isEqualTo(numbers.reduce { acc, d -> acc / d })
        }
    }
}