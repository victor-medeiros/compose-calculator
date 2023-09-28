package com.victor.composecalculator.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.victor.composecalculator.model.Operation
import com.victor.composecalculator.model.UiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CalculatorViewModel : ViewModel() {

    private val _calculatorUiState = MutableStateFlow(CalculatorUiState())
    val calculatorUiState: StateFlow<CalculatorUiState> = _calculatorUiState

    private var currentNumber = ""
    private val numbers = mutableListOf<Double>()
    private val operations = mutableListOf<Operation>()
    private var calculated = false

    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.AddDecimalCharacter -> {
                if (!currentNumber.contains(".")) {
                    currentNumber += "."
                    _calculatorUiState.update { currentState ->
                        currentState.copy(
                            expression = currentState.expression + "."
                        )
                    }
                }
            }

            is UiEvent.AddOperation -> {
                addCharacter(event.operation)
            }

            is UiEvent.CalculateOperation -> {
                if (currentNumber.isNotEmpty())
                    addNumber()

                if (numbers.size > 1 && currentNumber != "-") {
                    calculate()
                    calculated = true
                }
            }

            is UiEvent.TypeNumber -> {
                if (calculated) {
                    _calculatorUiState.update { it.copy(expression = "", result = 0.0) }
                    calculated = false
                }

                if (currentNumber == "0") {
                    currentNumber = event.number.toString()
                    _calculatorUiState.update { it.copy(expression = currentNumber) }
                } else {
                    currentNumber += event.number
                    _calculatorUiState.update { it.copy(expression = it.expression + event.number) }
                }
            }

            UiEvent.ClearExpression -> {
                _calculatorUiState.update { CalculatorUiState() }
                currentNumber = ""
                numbers.clear()
                operations.clear()
            }

            UiEvent.DeleteCharacter -> Log.d("BACKSPACE", "Not implemented yet")
        }
    }

    private fun addNumber() {
        numbers.add(currentNumber.toDouble())
        currentNumber = ""
    }

    private fun calculate() {
        var operation =
            operations.find { it == Operation.MULTIPLICATION || it == Operation.DIVISION }
        var priorityIndex = if (operation == null) -1 else operations.indexOf(operation)

        while (priorityIndex != -1) {
            val currentNumber = numbers[priorityIndex]
            val nextNumber = numbers[priorityIndex + 1]

            numbers[priorityIndex] = operation!!.calculate(currentNumber, nextNumber)
            numbers.removeAt(priorityIndex + 1)
            operations.removeAt(priorityIndex)

            operation =
                operations.find { it == Operation.MULTIPLICATION || it == Operation.DIVISION }
            priorityIndex = if (operation == null) -1 else operations.indexOf(operation)
        }

        _calculatorUiState.update { it.copy(result = numbers.sum()) }
        numbers.clear()
        operations.clear()
    }

    private fun addCharacter(operation: Operation) {
        if (calculated) {
            calculated = false
            val result = _calculatorUiState.value.result
            numbers.add(result)
            operations.add(operation)
            _calculatorUiState.update { currentState ->
                currentState.copy(
                    expression = result.toString() + operation.symbol,
                    result = 0.0
                )
            }
        } else if (currentNumber.isEmpty() || currentNumber == "-") {
            if (_calculatorUiState.value.expression.isEmpty()) return

            val expressionLastCharacter =
                _calculatorUiState.value.expression.last().toString()
            val operationFound = Operation.values().find { it.symbol == expressionLastCharacter }
            if (operationFound != null) {
                _calculatorUiState.update { currentState ->
                    currentState.copy(
                        expression = currentState.expression.dropLast(1) + operationFound.symbol
                    )
                }
            }
        } else {
            addNumber()
            operations.add(operation)
            _calculatorUiState.update { currentState ->
                currentState.copy(
                    expression = currentState.expression + operation.symbol
                )
            }
        }
        if (operation == Operation.SUBTRACTION && currentNumber != "-") {
            currentNumber += operation.symbol
        }
    }
}