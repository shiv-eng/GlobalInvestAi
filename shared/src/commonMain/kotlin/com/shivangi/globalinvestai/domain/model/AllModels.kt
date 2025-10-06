package com.shivangi.globalinvestai.domain.model

data class Stock(
    val ticker: String,
    val name: String,
    val price: Double,
    val change: Double,
    val changePercent: Double,
    val marketCap: String,
    val peRatio: Double?,
    val dividendYield: Double?,
    val week52High: Double,
    val week52Low: Double,
    val logo: String,
    val about: String,
    val analystRating: String,
    val financials: FinancialStatement,
    val analystOutlook: AnalystOutlook,
    val news: List<NewsArticle>,
    val aiNewsSummary: String
)

data class FinancialStatement(
    val aiSummary: String,
    val documentLinks: List<DocumentLink>
)

data class DocumentLink(
    val title: String,
    val url: String
)

data class AnalystOutlook(
    val rating: AnalystRating,
    val priceTarget: PriceTarget,
    val summary: String
)

data class AnalystRating(
    val buy: Int,
    val hold: Int,
    val sell: Int
)

data class PriceTarget(
    val high: Double,
    val low: Double,
    val average: Double
)

data class NewsArticle(
    val date: String,
    val source: String,
    val headline: String,
    val impact: String, // 'Positive', 'Negative', 'Neutral'
    val summary: String
)

data class PortfolioHolding(
    val stock: Stock,
    val shares: Int,
    val avgCost: Double,
    val totalValue: Double
)

data class AiPortfolio(
    val id: String,
    val name: String,
    val description: String,
    val cagr: Double,
    val risk: String // 'Low', 'Medium', 'High'
)

data class AiInsight(
    val id: String,
    val title: String,
    val content: String,
    val rationale: String,
    val timing: String,
    val horizon: String,
    val relatedStocks: List<Stock>
)