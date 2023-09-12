package com.victor.composecalculator

import androidx.lifecycle.ViewModel
import com.victor.composecalculator.model.UiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CalculatorViewModel: ViewModel() {

    private val _currentNumber = MutableStateFlow("")
    val currentNumber: StateFlow<String> = _currentNumber

    private val _numbers = MutableStateFlow(emptyList<Number>())
    val numbers: StateFlow<List<Number>> = _numbers

    private val _operations = MutableStateFlow(emptyList<String>())
    val operations: StateFlow<List<String>> = _operations

    private val _result = MutableStateFlow(0)
    val result: StateFlow<Number> = _result

    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.AddDecimalCharacter -> {}
            is UiEvent.AddOperation -> {}
            is UiEvent.CalculateOperation -> {}
            is UiEvent.TypeNumber -> {
                _currentNumber.value += event.number
            }
        }
    }
}