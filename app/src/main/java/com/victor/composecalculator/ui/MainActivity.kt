package com.victor.composecalculator.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.victor.composecalculator.ui.calculator.CalculatorScreen
import com.victor.composecalculator.ui.calculator.CalculatorViewModel
import com.victor.composecalculator.ui.theme.ComposeCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModel: CalculatorViewModel by viewModels()
                    CalculatorScreen(calculatorViewModel = viewModel)
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun DefaultPreview() {
    ComposeCalculatorTheme {
        val viewModel = CalculatorViewModel()
        CalculatorScreen(calculatorViewModel = viewModel)
    }
}