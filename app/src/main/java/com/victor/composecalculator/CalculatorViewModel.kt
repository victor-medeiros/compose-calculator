package com.victor.composecalculator

import androidx.lifecycle.ViewModel
import com.victor.composecalculator.model.Operation
import com.victor.composecalculator.model.UiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CalculatorViewModel : ViewModel() {

    private val _currentNumber = MutableStateFlow("")
    val currentNumber: StateFlow<String> = _currentNumber

    private val _numbers = MutableStateFlow(mutableListOf<Double>())
    val numbers: StateFlow<List<Double>> = _numbers

    private val _operations = MutableStateFlow(mutableListOf<Operation>())
    val operations: StateFlow<List<Operation>> = _operations

    private val _result = MutableStateFlow(0.0)
    val result: StateFlow<Double> = _result

    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.AddDecimalCharacter -> {
                if (!_currentNumber.value.contains(".")) {
                    _currentNumber.value += "."
                }
            }

            is UiEvent.AddOperation -> {
                if (_currentNumber.value.isNotEmpty()) {
                    addNumber()
                    _operations.value.add(event.operation)
                }
                if (event.operation == Operation.SUBTRACTION) {
                    _currentNumber.value += event.operation.symbol
                }
            }

            is UiEvent.CalculateOperation -> {
                addNumber()
                calculate()
            }

            is UiEvent.TypeNumber -> {
                if (_currentNumber.value == "0") {
                    _currentNumber.value = event.number.toString()
                } else {
                    _currentNumber.value += event.number
                }
            }
        }
    }

    private fun addNumber() {
        _numbers.value.add(_currentNumber.value.toDouble())
        _currentNumber.value = ""
    }

    private fun calculate() {
        var operation = _operations.value.find { it == Operation.MULTIPLICATION || it == Operation.DIVISION }
        var priorityIndex = if (operation == null) -1 else _operations.value.indexOf(operation)

        while (priorityIndex != -1) {
            val currentNumber = _numbers.value[priorityIndex]
            val nextNumber = _numbers.value[priorityIndex + 1]

            _numbers.value[priorityIndex] = operation!!.calculate(currentNumber, nextNumber)
            _numbers.value.removeAt(priorityIndex + 1)
            _operations.value.removeAt(priorityIndex)

            operation = _operations.value.find { it == Operation.MULTIPLICATION || it == Operation.DIVISION }
            priorityIndex = if (operation == null) -1 else _operations.value.indexOf(operation)
        }

        _result.value = _numbers.value.sum()
        _numbers.value.clear()
        _operations.value.clear()
    }
}