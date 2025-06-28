package com.victor.composecalculator.model

sealed class UiEvent {
    data class TypeNumber(val number: Int): UiEvent()
    data class AddOperation(val operation: Operation): UiEvent()
    object AddDecimalCharacter: UiEvent()
    object CalculateOperation: UiEvent()
    object ClearExpression: UiEvent()
    object DeleteCharacter: UiEvent()
}

enum class Operation(val symbol: String) {
    SUM(symbol = "+"),
    SUBTRACTION(symbol = "-"),
    MULTIPLICATION(symbol = "*"),
    DIVISION(symbol = "/"),
    PERCENTAGE(symbol = "%");

    fun calculate(n1: Double, n2: Double): Double {
        return when (this) {
            SUM -> n1 + n2
            SUBTRACTION -> n1 - n2
            MULTIPLICATION -> n1 * n2
            DIVISION -> n1 / n2
            PERCENTAGE -> (n1 * n2) / 100
        }
    }
}