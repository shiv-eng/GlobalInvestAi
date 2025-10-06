package com.shivangi.globalinvestai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.shivangi.globalinvestai.domain.model.Stock
import com.shivangi.globalinvestai.ui.components.StockChart
import com.shivangi.globalinvestai.ui.theme.Negative
import com.shivangi.globalinvestai.ui.theme.Positive
import com.shivangi.globalinvestai.ui.viewmodel.StockDetailState
import com.shivangi.globalinvestai.ui.viewmodel.StockDetailViewModel
import org.koin.core.parameter.parametersOf

data class StockDetailScreen(val ticker: String) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<StockDetailViewModel>(parameters = { parametersOf(ticker) })
        val state by viewModel.stockState.collectAsState()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Details") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                when (val stockState = state) {
                    is StockDetailState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    is StockDetailState.Error -> Text(stockState.message, modifier = Modifier.align(Alignment.Center))
                    is StockDetailState.Success -> {
                        StockDetailContent(stockState.stock)
                    }
                }
            }
        }
    }
}

@Composable
fun StockDetailContent(stock: Stock) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item { StockHeader(stock) }
        item { Spacer(modifier = Modifier.height(24.dp)) }
        item {
            StockChart(
                modifier = Modifier.fillMaxWidth().height(200.dp),
                isPositive = (stock.changePercent ?: 0.0) >= 0.0
            )
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
        item { KeyStatistics(stock) }
        item { Spacer(modifier = Modifier.height(24.dp)) }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Negative)
                ) {
                    Text("Sell")
                }
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Positive)
                ) {
                    Text("Buy")
                }
            }
        }
    }
}

@Composable
fun StockHeader(stock: Stock) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(stock.ticker ?: "-", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(stock.name ?: "-", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        }

        Column(horizontalAlignment = Alignment.End) {
            val priceText = stock.price?.let { "$${String.format("%.2f", it)}" } ?: "-"
            Text(priceText, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

            val change = stock.change ?: 0.0
            val changePercent = stock.changePercent ?: 0.0
            val isPositive = changePercent >= 0.0
            val color = if (stock.changePercent == null) Color.Gray else if (isPositive) Positive else Negative

            val changeText = if (stock.change != null && stock.changePercent != null) {
                "${if (isPositive) "+" else ""}${String.format("%.2f", change)} (${String.format("%.2f", changePercent)}%)"
            } else {
                "-"
            }

            Text(
                text = changeText,
                color = color,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun KeyStatistics(stock: Stock) {
    Column {
        Text("Key Statistics", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        StatRow("Market Cap", stock.marketCap ?: "N/A")
        StatRow("P/E Ratio", stock.peRatio?.let { String.format("%.2f", it) } ?: "N/A")
        StatRow("Dividend Yield", stock.dividendYield?.let { String.format("%.2f", it) + "%" } ?: "N/A")
        StatRow("52 Week High", stock.week52High?.let { "$${String.format("%.2f", it)}" } ?: "N/A")
        StatRow("52 Week Low", stock.week52Low?.let { "$${String.format("%.2f", it)}" } ?: "N/A")
    }
}

@Composable
fun StatRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = Color.Gray, style = MaterialTheme.typography.bodyMedium)
        Text(value, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodyMedium)
    }
    Divider()
}
