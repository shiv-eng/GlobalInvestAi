package com.shivangi.globalinvestai.data

import com.shivangi.globalinvestai.domain.model.*

object MockData {

    val aaplNews = listOf(
        NewsArticle("May 20, 2024", "Bloomberg", "Apple Shares Rise on Report of 'Groundbreaking' AI Features", "Positive", "A new report suggests significant AI enhancements at its upcoming developer conference."),
        NewsArticle("May 5, 2024", "Wall Street Journal", "EU Opens New Antitrust Probe into Apple's App Store Policies", "Negative", "Regulators are investigating Apple’s compliance with the Digital Markets Act.")
    )

    val googlNews = listOf(
        NewsArticle("May 22, 2024", "The Verge", "Google I/O Showcases 'AI-First' Future with Project Astra", "Positive", "Google demonstrated deep integration of its Gemini AI model across its product suite."),
        NewsArticle("May 12, 2024", "Financial Times", "Advertisers Raise Concerns Over AI-Generated Search Results", "Negative", "Advertisers are expressing concern that AI search summaries could reduce click-through rates.")
    )

    val aaplFinancials = FinancialStatement(
        aiSummary = "Over the past 3 years, Apple has demonstrated consistent revenue growth, primarily driven by strong iPhone sales and a rapidly expanding high-margin Services division. The company maintains an exceptionally strong balance sheet.",
        documentLinks = listOf(DocumentLink("Annual Reports", "#"))
    )

    val googlFinancials = FinancialStatement(
        aiSummary = "Alphabet's financial performance has been strong, with consistent double-digit revenue growth fueled by its core Search and YouTube advertising businesses. The Google Cloud segment has become a significant contributor.",
        documentLinks = listOf(DocumentLink("Annual Reports", "#"))
    )

    val mockStocks = listOf(
        Stock("AAPL", "Apple Inc.", 190.91, -1.50, -0.78, "3.1T", 30.5, 0.51, 199.62, 164.08, "https://picsum.photos/seed/AAPL/100", "Apple Inc. designs, manufactures, and markets smartphones, personal computers, tablets, and accessories.", "Buy", aaplFinancials, AnalystOutlook(AnalystRating(35, 10, 2), PriceTarget(250.0, 180.0, 215.50), "Analysts are broadly positive on Apple."), aaplNews, "Recent news is cautiously optimistic, driven by AI expectations but balanced by regulatory challenges."),
        Stock("GOOGL", "Alphabet Inc.", 178.54, 2.30, 1.30, "2.2T", 27.2, 0.45, 180.25, 115.83, "https://picsum.photos/seed/GOOGL/100", "Alphabet Inc. is a holding company and the parent company of Google, the global leader in online search.", "Buy", googlFinancials, AnalystOutlook(AnalystRating(45, 5, 0), PriceTarget(220.0, 180.0, 205.00), "The consensus for Alphabet is a strong buy."), googlNews, "The news narrative for Alphabet is strongly positive, centering on its push into AI with Gemini."),
        Stock("NVDA", "NVIDIA Corporation", 950.02, 15.80, 1.69, "2.3T", 76.5, 0.02, 974.00, 373.56, "https://picsum.photos/seed/NVDA/100", "NVIDIA Corporation designs and manufactures graphics processing units (GPUs). It is the leader in chips for AI.", "Buy", googlFinancials, AnalystOutlook(AnalystRating(50, 2, 1), PriceTarget(1200.0, 900.0, 1100.00), "NVIDIA has a near-unanimous 'Strong Buy' rating."), googlNews, "News for NVIDIA is strongly positive, centering on its AI dominance.")
    )

    val mockHoldings = listOf(
        PortfolioHolding(mockStocks[2], 50, 550.00, 50 * mockStocks[2].price),
        PortfolioHolding(mockStocks[0], 100, 170.00, 100 * mockStocks[0].price)
    )

    // THIS LIST WAS MISSING
    val mockAiPortfolios = listOf(
        AiPortfolio("1", "Global Robotics & AI", "Invest in companies at the forefront of the AI and robotics revolution.", 25.5, "High"),
        AiPortfolio("2", "Sustainable Energy Leaders", "A portfolio of global leaders in renewable energy and green technology.", 18.2, "Medium"),
        AiPortfolio("3", "Emerging Market Innovators", "High-growth potential companies from rapidly developing economies.", 22.1, "High"),
        AiPortfolio("4", "Global Dividend Payers", "Stable companies from around the world with a strong history of paying dividends.", 8.9, "Low")
    )

    val mockAiInsights = listOf(
        AiInsight("1", "Diversify your EV Exposure", "Consider diversifying with leading South Korean and German battery manufacturers.", "Your holdings are concentrated in a single EV maker. Diversifying into the supply chain reduces risk.", "The EV market is nearing an inflection point where battery technology is a key differentiator.", "Long-term (3-5 years)", listOf(mockStocks[1], mockStocks[2]))
    )
}