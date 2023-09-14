package com.victor.composecalculator

import androidx.lifecycle.ViewModel
import com.victor.composecalculator.model.UiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CalculatorViewModel: ViewModel() {

    private val _currentNumber = MutableStateFlow("")
    val currentNumber: StateFlow<String> = _currentNumber

    private val _numbers = MutableStateFlow(emptyList<Double>())
    val numbers: StateFlow<List<Double>> = _numbers

    private val _operations = MutableStateFlow(emptyList<String>())
    val operations: StateFlow<List<String>> = _operations

    private val _result = MutableStateFlow(0.0)
    val result: StateFlow<Double> = _result

    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.AddDecimalCharacter -> {
                if (!_currentNumber.value.contains(".")) {
                    _currentNumber.value += "."
                }
            }
            is UiEvent.AddOperation -> {}
            is UiEvent.CalculateOperation -> {}
            is UiEvent.TypeNumber -> {
                if (_currentNumber.value == "0") {
                    _currentNumber.value = event.number.toString()
                } else {
                    _currentNumber.value += event.number
                }
            }
        }
    }
}