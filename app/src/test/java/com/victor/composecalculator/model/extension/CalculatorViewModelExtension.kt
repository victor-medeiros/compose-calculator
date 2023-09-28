package com.victor.composecalculator.model.extension

import com.victor.composecalculator.model.Operation
import com.victor.composecalculator.ui.CalculatorViewModel
import com.victor.composecalculator.model.UiEvent

fun CalculatorViewModel.addDecimal(
    numberUnits: Int = (1..3).random(),
    decimalUnits: Int = (1..3).random()
): Double {
    var number = ""
    for (i in 0 until numberUnits) {
        val n = if (i == 0) (1..9).random() else (0..9).random()
        onEvent(UiEvent.TypeNumber(n))
        number += n
    }

    onEvent(UiEvent.AddDecimalCharacter)
    number += "."

    for (i in 0 until decimalUnits) {
        val n = if (i == 0) (1..9).random() else (0..9).random()
        onEvent(UiEvent.TypeNumber(n))
        number += n
    }

    return number.toDouble()
}

fun CalculatorViewModel.addRandomCalculation() {
    onEvent(UiEvent.TypeNumber((1..9).random()))
    onEvent(UiEvent.AddOperation(Operation.values().random()))
    onEvent(UiEvent.TypeNumber((0..9).random()))
    onEvent(UiEvent.CalculateOperation)
}