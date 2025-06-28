package com.victor.composecalculator.ui

import com.victor.composecalculator.R
import com.victor.composecalculator.model.Operation
import com.victor.composecalculator.model.UiEvent


data class CalculatorItem(
    val resource: Int,
    val event: UiEvent,
)

val characters = listOf(
    CalculatorItem(
        R.string.clear,
        event = UiEvent.ClearExpression
    ),
    CalculatorItem(
        R.string.percentage,
        event = UiEvent.AddOperation(Operation.PERCENTAGE)
    ),
    CalculatorItem(
        R.string.erase,
        event = UiEvent.DeleteCharacter
    ),
    CalculatorItem(
        R.string.sum,
        event = UiEvent.AddOperation(Operation.SUM)
    ),
    CalculatorItem(
        R.string.nine,
        event = UiEvent.TypeNumber(9)
    ),
    CalculatorItem(
        R.string.eight,
        event = UiEvent.TypeNumber(8)
    ),
    CalculatorItem(
        R.string.seven,
        event = UiEvent.TypeNumber(7)
    ),
    CalculatorItem(
        R.string.subtraction,
        event = UiEvent.AddOperation(Operation.SUBTRACTION)
    ),
    CalculatorItem(
        R.string.six,
        event = UiEvent.TypeNumber(6)
    ),
    CalculatorItem(
        R.string.five,
        event = UiEvent.TypeNumber(5)
    ),
    CalculatorItem(
        R.string.four,
        event = UiEvent.TypeNumber(4)
    ),
    CalculatorItem(
        R.string.multiplication,
        event = UiEvent.AddOperation(Operation.MULTIPLICATION)
    ),
    CalculatorItem(
        R.string.three,
        event = UiEvent.TypeNumber(3)
    ),
    CalculatorItem(
        R.string.two,
        event = UiEvent.TypeNumber(2)
    ),
    CalculatorItem(
        R.string.one,
        event = UiEvent.TypeNumber(1)
    ),
    CalculatorItem(
        R.string.division,
        event = UiEvent.AddOperation(Operation.DIVISION)
    ),
    CalculatorItem(
        R.string.zero,
        event = UiEvent.TypeNumber(0)
    ),
    CalculatorItem(
        R.string.dot,
        event = UiEvent.AddDecimalCharacter
    ),
    CalculatorItem(
        resource = R.string.equals,
        event = UiEvent.CalculateOperation
    )
)