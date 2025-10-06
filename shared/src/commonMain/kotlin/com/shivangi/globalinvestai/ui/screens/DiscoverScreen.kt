package com.shivangi.globalinvestai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.shivangi.globalinvestai.domain.model.Stock
import com.shivangi.globalinvestai.ui.components.StockListItem
import com.shivangi.globalinvestai.ui.viewmodel.DiscoverViewModel
import org.koin.compose.koinInject

object DiscoverScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: DiscoverViewModel = koinInject()
        val uiState by viewModel.uiState.collectAsState()

        var searchTerm by remember { mutableStateOf("") }
        var activeTab by remember { mutableStateOf("Top Gainers") }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Discover") },
                )
            }
        ) { paddingValues ->
            Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 16.dp)) {
                OutlinedTextField(
                    value = searchTerm,
                    onValueChange = { searchTerm = it },
                    label = { Text("Search stocks...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )

                TabRow(selectedTabIndex = when (activeTab) {
                    "Top Gainers" -> 0
                    "Top Losers" -> 1
                    else -> 2
                }) {
                    listOf("Top Gainers", "Top Losers", "Popular Brands").forEachIndexed { index, title ->
                        Tab(
                            selected = activeTab == title,
                            onClick = { activeTab = title },
                            text = { Text(title) }
                        )
                    }
                }

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(uiState.stocks) { stock ->
                        StockListItem(
                            stock = stock,
                            onClick = {
                                navigator.push(StockDetailScreen(stock.ticker))
                            }
                        )
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}