package com.shivangi.globalinvestai.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shivangi.globalinvestai.domain.model.Stock
import com.shivangi.globalinvestai.ui.theme.Negative
import com.shivangi.globalinvestai.ui.theme.Positive
import kotlin.math.roundToInt

@Composable
fun StockListItem(
    stock: Stock,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        alpha.animateTo(1f, animationSpec = tween(300))
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .alpha(alpha.value)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = stock.ticker ?: "-", fontWeight = FontWeight.Bold)
                Text(
                    text = stock.name ?: "-",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1
                )
            }

            Spacer(Modifier.width(16.dp))

            Box(modifier = Modifier.height(40.dp).width(80.dp)) {
                StockChart(isPositive = (stock.changePercent ?: 0.0) >= 0.0)
            }

            Spacer(Modifier.width(16.dp))

            Column(horizontalAlignment = Alignment.End) {
                val priceText = stock.price?.let {
                    "$" + ((it * 100.0).roundToInt() / 100.0).toString()
                } ?: "-"
                Text(priceText, fontWeight = FontWeight.SemiBold)

                val cp = stock.changePercent
                val changeText = if (cp != null) {
                    val sign = if (cp >= 0) "+" else ""
                    val rounded = (cp * 100.0).roundToInt() / 100.0
                    "$sign$rounded%"
                } else {
                    "-"
                }

                val isPositive = (stock.changePercent ?: 0.0) >= 0.0
                Text(
                    changeText,
                    color = if (cp == null) MaterialTheme.colorScheme.onSurface else if (isPositive) Positive else Negative,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}