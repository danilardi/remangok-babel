package com.ipb.remangokbabel.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.ipb.remangokbabel.data.repository.Repository
import com.ipb.remangokbabel.model.response.GetAllProductResponse
import com.ipb.remangokbabel.model.response.GetProfileResponse
import com.ipb.remangokbabel.ui.common.UiState
import com.ipb.remangokbabel.utils.handleException2
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : BaseViewModel() {
    private val _productState: MutableStateFlow<UiState<GetAllProductResponse>> =
        MutableStateFlow(UiState.Idle)
    private val _profileState: MutableStateFlow<UiState<GetProfileResponse>> =
        MutableStateFlow(UiState.Idle)

    val productState: MutableStateFlow<UiState<GetAllProductResponse>>
        get() = _productState
    val profileState: MutableStateFlow<UiState<GetProfileResponse>>
        get() = _profileState

    fun getProfile() {
        viewModelScope.launch {
            try {
                _profileState.value = UiState.Loading
                _profileState.value = UiState.Success(repository.getProfile())
            } catch (e: Exception) {
                _profileState.value = handleException2(e)
            }
        }
    }

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