package com.shivangi.globalinvestai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.shivangi.globalinvestai.domain.model.PortfolioHolding
import com.shivangi.globalinvestai.ui.components.StockListItem
import com.shivangi.globalinvestai.ui.viewmodel.PortfolioViewModel
import moe.tlaster.precompose.koin.koinViewModel

object PortfolioScreen : Screen {
    @Composable
    override fun Content() {
        PortfolioScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioScreenContent() {
    val viewModel = koinViewModel(vmClass = PortfolioViewModel::class)
    val holdings by viewModel.holdingsState.collectAsState()
    // Corrected the sumOf ambiguity by explicitly typing the lambda parameter
    val totalValue = holdings.sumOf { holding -> holding.totalValue }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My Portfolio") })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Text(
                    "Total Value: $${"%.2f".format(totalValue)}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            items(holdings) { holding ->
                // This was previously incorrect, now correctly defined
                HoldingItem(holding)
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun HoldingItem(holding: PortfolioHolding) {
    StockListItem(stock = holding.stock, onClick = { /* TODO: Navigate to detail */ })
}