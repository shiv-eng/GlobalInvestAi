package com.shivangi.globalinvestai.ui.viewmodel

import com.shivangi.globalinvestai.data.repository.StockRepository
import com.shivangi.globalinvestai.domain.model.Stock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class StockDetailViewModel(
    private val ticker: String,
    private val stockRepository: StockRepository
) : ViewModel() {

    private val _stockState = MutableStateFlow<StockDetailState>(StockDetailState.Loading)
    val stockState = _stockState.asStateFlow()

    init {
        loadStockDetails()
    }

    private fun loadStockDetails() {
        viewModelScope.launch {
            val stock = stockRepository.getStockByTicker(ticker)
            if (stock != null) {
                _stockState.value = StockDetailState.Success(stock)
            } else {
                _stockState.value = StockDetailState.Error("Stock not found")
            }
        }
    }
}

sealed interface StockDetailState {
    data object Loading : StockDetailState
    data class Success(val stock: Stock) : StockDetailState
    data class Error(val message: String) : StockDetailState
}