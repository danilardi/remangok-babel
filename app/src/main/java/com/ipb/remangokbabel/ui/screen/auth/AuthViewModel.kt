package com.ipb.remangokbabel.ui.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ipb.remangokbabel.data.repository.Repository
import com.ipb.remangokbabel.model.LoginRequest
import com.ipb.remangokbabel.model.LoginResponse
import com.ipb.remangokbabel.model.LogoutResponse
import com.ipb.remangokbabel.ui.common.UiState
import com.ipb.remangokbabel.utils.handleException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: Repository) : ViewModel() {
    private val _loginState: MutableStateFlow<UiState<LoginResponse>> =
        MutableStateFlow(UiState.Idle)
    private val _logoutState: MutableStateFlow<UiState<LogoutResponse>> =
        MutableStateFlow(UiState.Idle)

    val loginState: StateFlow<UiState<LoginResponse>>
        get() = _loginState
    val logoutState: StateFlow<UiState<LogoutResponse>>
        get() = _logoutState

    fun login(data: LoginRequest) {
        viewModelScope.launch {
            try {
                _loginState.value = UiState.Loading
                _loginState.value = UiState.Success(repository.login(data))
            } catch (e: Exception) {
                _loginState.value = handleException(e)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                _logoutState.value = UiState.Loading
                _logoutState.value = UiState.Success(repository.logout())
            } catch (e: Exception) {
                _logoutState.value = handleException(e)
            }
        }
    }
}