package com.shivangi.globalinvestai.ui.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.shivangi.globalinvestai.data.repository.StockRepository
import com.shivangi.globalinvestai.domain.model.PortfolioHolding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PortfolioViewModel(
    private val repository: StockRepository
) : ScreenModel {

    private val _holdingsState = MutableStateFlow<List<PortfolioHolding>>(emptyList())
    val holdingsState: StateFlow<List<PortfolioHolding>> = _holdingsState.asStateFlow()

    init {
        loadHoldings()
    }

    private fun loadHoldings() {
        screenModelScope.launch {
            try {
                _holdingsState.value = repository.getPortfolioHoldings()
            } catch (e: Exception) {
                // Handle error - you might want to add an error state
                _holdingsState.value = emptyList()
            }
        }
    }

    fun refreshHoldings() {
        loadHoldings()
    }
}