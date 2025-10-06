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
import coil3.compose.AsyncImage
import com.shivangi.globalinvestai.domain.model.Stock
import com.shivangi.globalinvestai.ui.viewmodel.StockDetailState
import com.shivangi.globalinvestai.ui.viewmodel.StockDetailViewModel
import com.shivangi.globalinvestai.ui.theme.Negative
import com.shivangi.globalinvestai.ui.theme.Positive
import org.koin.core.parameter.parametersOf

data class StockDetailScreen(val ticker: String) : Screen {
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
        item { AboutSection(stock) }
        item { Spacer(modifier = Modifier.height(24.dp)) }
        item { KeyStatistics(stock) }
    }
}

@Composable
fun StockHeader(stock: Stock) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            model = stock.logo,
            contentDescription = "Stock Logo",
            modifier = Modifier.size(56.dp).clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(stock.ticker, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(stock.name, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text("$${"%.2f".format(stock.price)}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            val isPositive = stock.changePercent >= 0
            val color = if (isPositive) Positive else Negative
            Text(
                "${if (isPositive) "+" else ""}${"%.2f".format(stock.change)} (${"%.2f".format(stock.changePercent)}%)",
                color = color,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun AboutSection(stock: Stock) {
    Column {
        Text("About", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(stock.about, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
    }
}

@Composable
fun KeyStatistics(stock: Stock) {
    Column {
        Text("Key Statistics", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        StatRow("Market Cap", stock.marketCap)
        StatRow("P/E Ratio", stock.peRatio?.toString() ?: "N/A")
        StatRow("Dividend Yield", stock.dividendYield?.let { "%.2f".format(it) + "%" } ?: "N/A")
        StatRow("52 Week High", "$${"%.2f".format(stock.week52High)}")
        StatRow("52 Week Low", "$${"%.2f".format(stock.week52Low)}")
    }
}

@Composable
fun StatRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray, style = MaterialTheme.typography.bodyMedium)
        Text(value, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodyMedium)
    }
    Divider()
}