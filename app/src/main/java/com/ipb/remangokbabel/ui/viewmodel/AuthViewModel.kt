package com.ipb.remangokbabel.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ipb.remangokbabel.data.repository.Repository
import com.ipb.remangokbabel.model.request.LoginRequest
import com.ipb.remangokbabel.model.request.RegisterRequest
import com.ipb.remangokbabel.model.response.ErrorResponse
import com.ipb.remangokbabel.model.response.LoginResponse
import com.ipb.remangokbabel.model.response.RegisterResponse
import com.ipb.remangokbabel.model.response.StatusMessageResponse
import com.ipb.remangokbabel.utils.handleException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: Repository) : ViewModel() {

    private val _showLoading = MutableSharedFlow<Boolean>()
    private val _errorResponse = MutableSharedFlow<ErrorResponse>()
    val showLoading: SharedFlow<Boolean>
        get() = _showLoading
    val errorResponse: SharedFlow<ErrorResponse>
        get() = _errorResponse

    private val _loginResponse = MutableSharedFlow<LoginResponse>()
    private val _registerResponse = MutableSharedFlow<RegisterResponse>()
    private val _logoutResponse = MutableSharedFlow<StatusMessageResponse>()

    val loginResponse: SharedFlow<LoginResponse>
        get() = _loginResponse
    val registerResponse: SharedFlow<RegisterResponse>
        get() = _registerResponse
    val logoutResponse: SharedFlow<StatusMessageResponse>
        get() = _logoutResponse

    fun login(data: LoginRequest) {
        viewModelScope.launch {
            _showLoading.emit(true)
            try {
                _loginResponse.emit(repository.login(data))
            } catch (e: Exception) {
                _errorResponse.emit(handleException(e))
            }
            _showLoading.emit(false)
        }
    }

    fun register(data: RegisterRequest) {
        viewModelScope.launch {
            _showLoading.emit(true)
            try {
                _registerResponse.emit(repository.register(data))
            } catch (e: Exception) {
                _errorResponse.emit(handleException(e))
            }
            _showLoading.emit(false)
        }
    }

    fun logout() {
        viewModelScope.launch {
            _showLoading.emit(true)
            try {
                _logoutResponse.emit(repository.logout())
            } catch (e: Exception) {
                _errorResponse.emit(handleException(e))
            }
            _showLoading.emit(false)
        }
    }
}