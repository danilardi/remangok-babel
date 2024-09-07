package com.ipb.remangokbabel.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.ipb.remangokbabel.data.repository.Repository
import com.ipb.remangokbabel.model.request.LoginRequest
import com.ipb.remangokbabel.model.request.RegisterRequest
import com.ipb.remangokbabel.model.response.LoginResponse
import com.ipb.remangokbabel.model.response.RegisterResponse
import com.ipb.remangokbabel.model.response.StatusMessageResponse
import com.ipb.remangokbabel.utils.handleException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: Repository) : BaseViewModel() {

//    private val _showLoading = MutableSharedFlow<Boolean>()
//    private val _errorResponse = MutableSharedFlow<ErrorResponse>()
//    val showLoading: SharedFlow<Boolean>
//        get() = _showLoading
//    val errorResponse: SharedFlow<ErrorResponse>
//        get() = _errorResponse
//
//    private val _loginResponse = MutableSharedFlow<LoginResponse>()
//    private val _registerResponse = MutableSharedFlow<RegisterResponse>()
//    private val _logoutResponse = MutableSharedFlow<StatusMessageResponse>()
//
//    val loginResponse: SharedFlow<LoginResponse>
//        get() = _loginResponse
//    val registerResponse: SharedFlow<RegisterResponse>
//        get() = _registerResponse
//    val logoutResponse: SharedFlow<StatusMessageResponse>
//        get() = _logoutResponse

    val loginResponse = MutableSharedFlow<LoginResponse>()
    val registerResponse = MutableSharedFlow<RegisterResponse>()
    val logoutResponse = MutableSharedFlow<StatusMessageResponse>()

    fun login(data: LoginRequest) {
        viewModelScope.launch {
            showLoading.emit(true)
            try {
                loginResponse.emit(repository.login(data))
            } catch (e: Exception) {
                errorResponse.emit(handleException(e))
            }
            showLoading.emit(false)
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

    fun logout() {
        viewModelScope.launch {
            showLoading.emit(true)
            try {
                logoutResponse.emit(repository.logout())
            } catch (e: Exception) {
                errorResponse.emit(handleException(e))
            }
            showLoading.emit(false)
        }
    }
}