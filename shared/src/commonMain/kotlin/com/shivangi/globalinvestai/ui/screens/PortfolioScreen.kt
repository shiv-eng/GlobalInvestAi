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
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.shivangi.globalinvestai.ui.components.StockListItem
import com.shivangi.globalinvestai.ui.viewmodel.PortfolioViewModel

object PortfolioScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<PortfolioViewModel>()
        val holdings by viewModel.holdingsState.collectAsState()
        val totalValue = holdings.sumOf { it.totalValue }

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
                        "Total Value: $${(totalValue)}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                items(holdings) { holding ->
                    StockListItem(
                        stock = holding.stock,
                        onClick = {
                            navigator.push(StockDetailScreen(holding.stock.ticker))
                        }
                    )
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}