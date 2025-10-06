package com.shivangi.globalinvestai.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
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
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
          /*  AsyncImage(
                model = stock.logo,
                contentDescription = "${stock.name ?: "Logo"} Logo",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )*/

            Spacer(Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = stock.ticker ?: "-", fontWeight = FontWeight.Bold)
                Text(
                    text = stock.name ?: "-",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1
                )
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
