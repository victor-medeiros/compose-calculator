package com.victor.composecalculator.ui.calculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.victor.composecalculator.model.UiEvent
import com.victor.composecalculator.ui.calculator.component.CalculatorButton
import com.victor.composecalculator.ui.characters
import com.victor.composecalculator.ui.theme.ComposeCalculatorTheme
import com.victor.composecalculator.ui.theme.Gray600
import com.victor.composecalculator.ui.theme.Orange400
import com.victor.composecalculator.ui.theme.Yellow400

private const val TOTAL_COLUMNS = 4

@Composable
fun CalculatorScreen(calculatorViewModel: CalculatorViewModel) {
    val uiState by calculatorViewModel.calculatorUiState.collectAsState()
    CalculatorComponent(
        uiState = uiState,
        onButtonPress = calculatorViewModel::onEvent
    )
}

@Composable
private fun CalculatorComponent(uiState: CalculatorUiState, onButtonPress: (UiEvent) -> Unit) {
    val gap = 16.dp

    Box(modifier = Modifier.fillMaxSize().systemBarsPadding()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                uiState.expression,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                uiState.result.toString(),
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onBackground
            )
        }

        LazyVerticalGrid(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            columns = GridCells.Fixed(TOTAL_COLUMNS),
            horizontalArrangement = Arrangement.spacedBy(gap),
            verticalArrangement = Arrangement.spacedBy(gap)
        ) {
            items((0..17).toList()) {
                val color = if (it <= 3 || (it - 3) % 4 == 0) MaterialTheme.colors.onSecondary
                else MaterialTheme.colors.surface
                CalculatorButton(
                    modifier = Modifier.aspectRatio(1f),
                    text = stringResource(characters[it].resource),
                    textColor = MaterialTheme.colors.onSurface,
                    onPress = { onButtonPress(characters[it].event) },
                    backgroundColor = SolidColor(color)
                )
            }
            item(
                span = { GridItemSpan(2) }
            ) {
                CalculatorButton(
                    modifier = Modifier.aspectRatio(2.2f),
                    text = stringResource(characters[18].resource),
                    textColor = Gray600,
                    onPress = { onButtonPress(characters[18].event) },
                    backgroundColor = Brush.horizontalGradient(listOf(Orange400, Yellow400))
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CalculatorScreenPreview() {
    val calculatorViewModel = CalculatorViewModel()
    ComposeCalculatorTheme {
        CalculatorScreen(calculatorViewModel)
    }
}