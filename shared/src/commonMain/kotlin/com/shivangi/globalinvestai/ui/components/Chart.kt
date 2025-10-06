package com.shivangi.globalinvestai.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryOf
import com.shivangi.globalinvestai.ui.theme.Negative
import com.shivangi.globalinvestai.ui.theme.Positive
import kotlin.random.Random

@Composable
fun StockChart(modifier: Modifier = Modifier, isPositive: Boolean) {
    val modelProducer = remember { ChartEntryModelProducer() }
    val datasetForModel = remember {
        List(100) { entryOf(it, Random.nextFloat() * 100) }
    }

    modelProducer.setEntries(datasetForModel)

    val color = if (isPositive) Positive else Negative

    Chart(
        chart = lineChart(
            lines = listOf(
                LineChart.LineSpec(
                    lineColor = color.toArgb(), // Corrected: Used .toArgb()
                    lineBackgroundShader = DynamicShaders.fromBrush(
                        brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                            colors = listOf(
                                color.copy(alpha = 0.4f),
                                color.copy(alpha = 0.0f)
                            )
                        )
                    ),
                )
            )
        ),
        chartModelProducer = modelProducer,
        modifier = modifier,
        startAxis = startAxis(),
    )
}