package com.victor.composecalculator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.victor.composecalculator.model.UiEvent
import com.victor.composecalculator.ui.component.CalculatorButton
import com.victor.composecalculator.ui.theme.Gray600
import com.victor.composecalculator.ui.theme.Orange400
import com.victor.composecalculator.ui.theme.Yellow400

@Composable
fun CalculatorScreen(calculatorViewModel: CalculatorViewModel) {
    val uiState by calculatorViewModel.calculatorUiState.collectAsState()
    CalculatorComponent(
        uiState = uiState,
        onButtonPress = { calculatorViewModel.onEvent(it) }
    )
}

@Composable
private fun CalculatorComponent(uiState: CalculatorUiState, onButtonPress: (UiEvent) -> Unit) {
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

        LazyVerticalGrid(
            columns = GridCells.Adaptive(80.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (i in (0..2)) {
                        val modifier =
                            if (i == 0) Modifier.fillMaxWidth(0.5f) else Modifier.aspectRatio(1f)

                        CalculatorButton(
                            modifier = modifier,
                            resource = characters[i].resource,
                            symbolColor = MaterialTheme.colors.onSurface,
                            onPress = { onButtonPress(characters[i].event) },
                            color = SolidColor(MaterialTheme.colors.onSecondary)
                        )
                    }

                }
            }
            items((3..14).toList()) {
                val color = if ((it - 2) % 4 == 0) MaterialTheme.colors.onSecondary
                    else MaterialTheme.colors.surface
                CalculatorButton(
                    modifier = Modifier.aspectRatio(1f),
                    resource = characters[it].resource,
                    symbolColor = MaterialTheme.colors.onSurface,
                    onPress = { onButtonPress(characters[it].event) },
                    color = SolidColor(color)
                )
            }
            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (i in (15..17)) {
                        val modifier =
                            if (i == 17) Modifier.fillMaxWidth() else Modifier.aspectRatio(1f)
                        val color = if (i == 17) Brush.horizontalGradient(
                            listOf(Orange400, Yellow400)
                        ) else SolidColor(MaterialTheme.colors.surface)
                        val symbolColor = if (i == 17) Gray600 else MaterialTheme.colors.onPrimary

                        CalculatorButton(
                            modifier = modifier,
                            resource = characters[i].resource,
                            symbolColor = symbolColor,
                            onPress = { onButtonPress(characters[i].event) },
                            color = color
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CalculatorScreenPreview() {
    val calculatorViewModel = CalculatorViewModel()
    CalculatorScreen(calculatorViewModel)
}