package com.shivangi.globalinvestai.ui.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.shivangi.globalinvestai.data.repository.StockRepository
import com.shivangi.globalinvestai.domain.model.Stock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class StockDetailState {
    object Loading : StockDetailState()
    data class Success(val stock: Stock) : StockDetailState()
    data class Error(val message: String) : StockDetailState()
}

class StockDetailViewModel(
    private val ticker: String,
    private val repository: StockRepository
) : ScreenModel {

    private val _stockState = MutableStateFlow<StockDetailState>(StockDetailState.Loading)
    val stockState: StateFlow<StockDetailState> = _stockState.asStateFlow()

    init {
        loadStockDetails()
    }

    private fun loadStockDetails() {
        screenModelScope.launch {
            try {
                _stockState.value = StockDetailState.Loading
                val stock = repository.getStockByTicker(ticker)
                _stockState.value = if (stock != null) {
                    StockDetailState.Success(stock)
                } else {
                    StockDetailState.Error("Stock not found")
                }
            } catch (e: Exception) {
                _stockState.value = StockDetailState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun refresh() {
        loadStockDetails()
    }
}