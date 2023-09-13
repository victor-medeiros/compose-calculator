package com.victor.composecalculator.model

sealed class UiEvent {
    data class TypeNumber(val number: Int): UiEvent()
    data class AddOperation(val operation: Operation): UiEvent()
    object AddDecimalCharacter: UiEvent()
    object CalculateOperation: UiEvent()
}

enum class Operation(val symbol: String) {
    SUM("+"),
    SUBTRACTION("-"),
    MULTIPLICATION("*"),
    DIVISION("/")
}