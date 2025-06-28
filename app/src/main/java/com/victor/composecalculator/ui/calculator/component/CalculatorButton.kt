package com.victor.composecalculator.ui.calculator.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.victor.composecalculator.R
import com.victor.composecalculator.ui.theme.ComposeCalculatorTheme

@Composable
fun CalculatorButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = MaterialTheme.colors.onSurface,
    backgroundColor: Brush = SolidColor(MaterialTheme.colors.surface),
    onPress: () -> Unit
) {
    Box(
        modifier = modifier
            .clickable { onPress() }
            .width(80.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body1,
            color = textColor
        )
    }
}

@Preview
@Composable
private fun CalculatorButtonPreview() {
    ComposeCalculatorTheme {
        Row(
            Modifier
                .background(Color.LightGray)
                .width(300.dp)
                .padding(20.dp)
        ) {
            CalculatorButton(
                modifier = Modifier
                    .weight(2f)
                    .aspectRatio(2f),
                text = stringResource(R.string.two),
                onPress = {}
            )
            Spacer(modifier = Modifier.width(20.dp))
            CalculatorButton(
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                text = stringResource(R.string.erase),
                onPress = {}
            )
        }
    }
}