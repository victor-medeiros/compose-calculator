package com.victor.composecalculator.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.victor.composecalculator.R

@Composable
fun CalculatorButton(
    modifier: Modifier = Modifier,
    resource: Int,
    symbolColor: Color = MaterialTheme.colors.surface,
    color: Brush = SolidColor(MaterialTheme.colors.surface),
    onPress: () -> Unit
) {
    Box(
        modifier = modifier
            .height(80.dp)
            .clip(CircleShape)
            .background(color)
            .padding(16.dp)
            .clickable { onPress() },
        contentAlignment = Alignment.Center
    ) {
        val type = LocalContext.current.resources.getResourceTypeName(resource)
        if (type == "drawable") {
            Icon(
                painter = painterResource(id = resource),
                contentDescription = "",
                tint = symbolColor,
                modifier = Modifier.size(36.dp)
            )
        } else {
            Text(
                text = stringResource(resource),
                style = MaterialTheme.typography.body1,
                color = symbolColor
            )
        }
    }
}

@Preview(widthDp = 200, heightDp = 80)
@Composable
private fun CalculatorButtonPreview() {
    Row(
        Modifier
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        CalculatorButton(
            Modifier
                .aspectRatio(1f),
            resource = R.string.two,
            symbolColor = MaterialTheme.colors.onSurface,
            onPress = {}
        )
        Spacer(modifier = Modifier.width(8.dp))
        CalculatorButton(
            Modifier
                .aspectRatio(2f),
            resource = R.string.two,
            symbolColor = MaterialTheme.colors.onSurface,
            onPress = {}
        )
    }
}