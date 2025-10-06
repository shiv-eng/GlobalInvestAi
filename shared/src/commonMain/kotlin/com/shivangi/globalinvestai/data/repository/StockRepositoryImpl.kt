package com.shivangi.globalinvestai.data.repository

import com.shivangi.globalinvestai.data.MockData
import com.shivangi.globalinvestai.domain.model.AiInsight
import com.shivangi.globalinvestai.domain.model.AiPortfolio
import com.shivangi.globalinvestai.domain.model.PortfolioHolding
import com.shivangi.globalinvestai.domain.model.Stock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class StockRepositoryImpl : StockRepository {
    override suspend fun getPortfolioHoldings(): List<PortfolioHolding> = withContext(Dispatchers.IO) {
        delay(500) // Simulate network delay
        MockData.mockHoldings
    }

    override suspend fun getWatchlist(): List<Stock> = withContext(Dispatchers.IO) {
        delay(500)
        MockData.mockStocks.take(4)
    }

    override suspend fun getStockByTicker(ticker: String): Stock? = withContext(Dispatchers.IO) {
        delay(500)
        MockData.mockStocks.find { it.ticker.equals(ticker, ignoreCase = true) }
    }

    override suspend fun getAiInsight(): AiInsight? = withContext(Dispatchers.IO) {
        delay(300)
        MockData.mockAiInsights.firstOrNull()
    }

    override suspend fun getAiPortfolios(): List<AiPortfolio> = withContext(Dispatchers.IO) {
        delay(400)
        MockData.mockAiPortfolios // This line will now resolve correctly
    }
}