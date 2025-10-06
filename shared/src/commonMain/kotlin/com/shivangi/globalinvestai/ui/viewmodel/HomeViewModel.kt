package com.shivangi.globalinvestai.ui.viewmodel

import com.shivangi.globalinvestai.data.repository.StockRepository
import com.shivangi.globalinvestai.domain.model.AiInsight
import com.shivangi.globalinvestai.domain.model.AiPortfolio
import com.shivangi.globalinvestai.domain.model.PortfolioHolding
import com.shivangi.globalinvestai.domain.model.Stock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class HomeViewModel(private val stockRepository: StockRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val holdings = stockRepository.getPortfolioHoldings()
                val watchlist = stockRepository.getWatchlist()
                val insight = stockRepository.getAiInsight()
                val aiPortfolios = stockRepository.getAiPortfolios()

                val totalValue = holdings.sumOf { it.totalValue }

                _uiState.value = HomeUiState.Success(
                    portfolioValue = totalValue,
                    holdings = holdings,
                    watchlist = watchlist,
                    aiInsight = insight,
                    aiPortfolios = aiPortfolios
                )
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error("Failed to load data: ${e.message}")
            }
        }
    }
}

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Error(val message: String) : HomeUiState
    data class Success(
        val portfolioValue: Double,
        val holdings: List<PortfolioHolding>,
        val watchlist: List<Stock>,
        val aiInsight: AiInsight?,
        val aiPortfolios: List<AiPortfolio>
    ) : HomeUiState
}