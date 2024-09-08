package com.ipb.remangokbabel.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.ipb.remangokbabel.data.repository.Repository
import com.ipb.remangokbabel.model.request.LoginRequest
import com.ipb.remangokbabel.model.request.RegisterRequest
import com.ipb.remangokbabel.model.response.LoginResponse
import com.ipb.remangokbabel.model.response.RegisterResponse
import com.ipb.remangokbabel.utils.handleException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: Repository) : BaseViewModel() {

    val loginResponse = MutableSharedFlow<LoginResponse>()
    val registerResponse = MutableSharedFlow<RegisterResponse>()

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
}