package com.shivangi.globalinvestai.ui.viewmodel

import com.shivangi.globalinvestai.data.repository.StockRepository
import com.shivangi.globalinvestai.domain.model.PortfolioHolding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class PortfolioViewModel(private val stockRepository: StockRepository) : ViewModel() {

    private val _holdingsState = MutableStateFlow<List<PortfolioHolding>>(emptyList())
    val holdingsState = _holdingsState.asStateFlow()

    init {
        loadHoldings()
    }

    private fun loadHoldings() {
        viewModelScope.launch {
            _holdingsState.value = stockRepository.getPortfolioHoldings()
        }
    }
}