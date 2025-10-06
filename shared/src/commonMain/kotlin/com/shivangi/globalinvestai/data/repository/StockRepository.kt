package com.shivangi.globalinvestai.data.repository

import com.shivangi.globalinvestai.domain.model.AiInsight
import com.shivangi.globalinvestai.domain.model.AiPortfolio
import com.shivangi.globalinvestai.domain.model.PortfolioHolding
import com.shivangi.globalinvestai.domain.model.Stock

interface StockRepository {
    suspend fun getPortfolioHoldings(): List<PortfolioHolding>
    suspend fun getWatchlist(): List<Stock>
    suspend fun getStockByTicker(ticker: String): Stock?
    suspend fun getAiInsight(): AiInsight?
    suspend fun getAiPortfolios(): List<AiPortfolio>
}