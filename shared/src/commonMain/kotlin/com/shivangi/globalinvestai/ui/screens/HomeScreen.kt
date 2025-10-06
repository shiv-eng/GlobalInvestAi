package com.shivangi.globalinvestai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.shivangi.globalinvestai.domain.model.AiInsight
import com.shivangi.globalinvestai.ui.components.*
import com.shivangi.globalinvestai.ui.theme.*
import com.shivangi.globalinvestai.ui.viewmodel.HomeUiState
import com.shivangi.globalinvestai.ui.viewmodel.HomeViewModel
import org.koin.compose.koinInject


object HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: HomeViewModel = koinInject()
        val uiState by viewModel.uiState.collectAsState()

        HomeScreenContent(uiState = uiState, navigator = navigator)
    }
}

@Composable
fun HomeScreenContent(uiState: HomeUiState, navigator: Navigator) {
    when (uiState) {
        is HomeUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is HomeUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${uiState.message}")
            }
        }
        is HomeUiState.Success -> {
            LazyColumn(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)) {
                item { HomeHeader(portfolioValue = uiState.portfolioValue) }
                item {
                    uiState.aiInsight?.let {
                        AiInsightCard(insight = it, modifier = Modifier.padding(16.dp))
                    }
                }
                item {
                    Text(
                        "AI-Managed Portfolios",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
                    )
                }
                item {
                    val gradients = listOf(
                        Brush.linearGradient(listOf(Color(0xFFE0F7FA), Color(0xFFB2EBF2))),
                        Brush.linearGradient(listOf(Color(0xFFE8F5E9), Color(0xFFC8E6C9))),
                        Brush.linearGradient(listOf(Color(0xFFF3E5F5), Color(0xFFE1BEE7))),
                        Brush.linearGradient(listOf(Color(0xFFFFFDE7), Color(0xFFFFF59D))),
                    )
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(uiState.aiPortfolios) { portfolio ->
                            AiPortfolioCard(portfolio, gradients[uiState.aiPortfolios.indexOf(portfolio) % gradients.size])
                        }
                    }
                }

                item {
                    Text(
                        "Watchlist",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 8.dp)
                    )
                }
                items(uiState.watchlist) { stock ->
                    StockListItem(
                        stock = stock,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                        onClick = { navigator.push(StockDetailScreen(stock.ticker)) }
                    )
                }
            }
        }
    }
}
@Composable
fun HomeHeader(portfolioValue: Double) {
    val totalGain = 1250.75
    val totalGainPercent = (totalGain / (portfolioValue - totalGain)) * 100

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(colors = listOf(Primary, PrimaryDark))
            )
            .padding(24.dp)
    ) {
        Column {
            Text("Portfolio Value", color = TextPrimary.copy(alpha = 0.7f), fontSize = 14.sp)
            Text(
                "$${"%.2f".format(portfolioValue)}",
                color = TextPrimary,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = ArrowUpIcon,
                    contentDescription = "Up",
                    tint = TextPrimary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    "$${"%.2f".format(totalGain)} (${"%.2f".format(totalGainPercent)}%) Today",
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun AiInsightCard(insight: AiInsight, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Secondary)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Icon(
                imageVector = SparklesIcon,
                contentDescription = "AI Insight",
                tint = Primary,
                modifier = Modifier.size(24.dp).padding(top = 4.dp)
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    insight.title,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryDark
                )
                Text(
                    insight.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = PrimaryDark.copy(alpha = 0.8f),
                    modifier = Modifier.padding(top = 4.dp)
                )
                TextButton(onClick = { /* TODO */ }, contentPadding = PaddingValues(0.dp)) {
                    Text("Explore Now", fontWeight = FontWeight.Bold, color = Primary)
                }
            }
        }
    }
}