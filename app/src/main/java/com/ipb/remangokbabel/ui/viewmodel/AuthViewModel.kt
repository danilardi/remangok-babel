package com.ipb.remangokbabel.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.ipb.remangokbabel.data.repository.Repository
import com.ipb.remangokbabel.model.request.LoginRequest
import com.ipb.remangokbabel.model.request.RegisterRequest
import com.ipb.remangokbabel.model.response.LoginResponse
import com.ipb.remangokbabel.model.response.RegisterResponse
import com.ipb.remangokbabel.ui.common.UiState
import com.ipb.remangokbabel.utils.handleException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: Repository) : BaseViewModel() {

    val loginState = MutableStateFlow<UiState<LoginResponse>>(UiState.Idle)
    val registerResponse = MutableSharedFlow<RegisterResponse>()

    fun login(data: LoginRequest) {
        viewModelScope.launch {
            showLoading2.value = true
            try {
                loginState.value = UiState.Success(repository.login(data))
            } catch (e: Exception) {
                errorResponse2.value = handleException(e)
            }
            showLoading2.value = false
        }
    }

    fun register(data: RegisterRequest) {
        viewModelScope.launch {
            showLoading.emit(true)
            try {
                registerResponse.emit(repository.register(data))
            } catch (e: Exception) {
                errorResponse.emit(handleException(e))
            }
            showLoading.emit(false)
        }
    }
}