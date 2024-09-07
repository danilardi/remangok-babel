package com.ipb.remangokbabel.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ipb.remangokbabel.data.repository.Repository
import com.ipb.remangokbabel.model.response.ProductResponse
import com.ipb.remangokbabel.ui.common.UiState
import com.ipb.remangokbabel.utils.handleException2
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {
    private val _productState: MutableStateFlow<UiState<ProductResponse>> =
        MutableStateFlow(UiState.Idle)

    val productState: MutableStateFlow<UiState<ProductResponse>>
        get() = _productState

    fun getAllProducts(limit: Int = 10, offset: Int = 0) {
        viewModelScope.launch {
            try {
                _productState.value = UiState.Loading
                _productState.value = UiState.Success(repository.getAllProducts(limit, offset))
            } catch (e: Exception) {
                _productState.value = handleException2(e)
            }
        }
    }
}