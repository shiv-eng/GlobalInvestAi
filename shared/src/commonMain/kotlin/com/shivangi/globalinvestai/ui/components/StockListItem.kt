package com.shivangi.globalinvestai.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.shivangi.globalinvestai.domain.model.Stock
import com.shivangi.globalinvestai.ui.theme.Negative
import com.shivangi.globalinvestai.ui.theme.Positive

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
            // This is the corrected, public API for Coil
            AsyncImage(
                model = stock.logo,
                contentDescription = "${stock.name} Logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(stock.ticker, fontWeight = FontWeight.Bold)
                Text(stock.name, style = MaterialTheme.typography.bodySmall, maxLines = 1)
            }
            Spacer(Modifier.width(16.dp))
            Column(horizontalAlignment = Alignment.End) {
                Text("$${"%.2f".format(stock.price)}", fontWeight = FontWeight.SemiBold)
                val isPositive = stock.changePercent >= 0
                Text(
                    "${if (isPositive) "+" else ""}${"%.2f".format(stock.changePercent)}%",
                    color = if (isPositive) Positive else Negative,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}