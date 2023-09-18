package com.victor.composecalculator.ui

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
                if (currentNumber.isNotEmpty()) {
                    addNumber()
                    operations.add(event.operation)
                    _calculatorUiState.update { currentState ->
                        currentState.copy(
                            expression = currentState.expression + event.operation.symbol
                        )
                    }
                }
                if (event.operation == Operation.SUBTRACTION) {
                    currentNumber += event.operation.symbol
                }
            }

            is UiEvent.CalculateOperation -> {
                addNumber()
                calculate()
            }

            is UiEvent.TypeNumber -> {
                if (currentNumber == "0") {
                    currentNumber = event.number.toString()
                    _calculatorUiState.update { currentState ->
                        currentState.copy(
                            expression = currentNumber
                        )
                    }
                } else {
                    currentNumber += event.number
                    _calculatorUiState.update { currentState ->
                        currentState.copy(
                            expression = currentState.expression + event.number
                        )
                    }
                }
            }

            UiEvent.ClearExpression -> {
                _calculatorUiState.update { CalculatorUiState()}
                currentNumber = ""
                numbers.clear()
                operations.clear()
            }
        }
    }

    private fun addNumber() {
        numbers.add(currentNumber.toDouble())
        currentNumber = ""
    }

    private fun calculate() {
        var operation = operations.find { it == Operation.MULTIPLICATION || it == Operation.DIVISION }
        var priorityIndex = if (operation == null) -1 else operations.indexOf(operation)

        while (priorityIndex != -1) {
            val currentNumber = numbers[priorityIndex]
            val nextNumber = numbers[priorityIndex + 1]

            numbers[priorityIndex] = operation!!.calculate(currentNumber, nextNumber)
            numbers.removeAt(priorityIndex + 1)
            operations.removeAt(priorityIndex)

            operation = operations.find { it == Operation.MULTIPLICATION || it == Operation.DIVISION }
            priorityIndex = if (operation == null) -1 else operations.indexOf(operation)
        }

        _calculatorUiState.update {currentState ->
            currentState.copy(
                result = numbers.sum()
            )
        }
        numbers.clear()
        operations.clear()
    }
}