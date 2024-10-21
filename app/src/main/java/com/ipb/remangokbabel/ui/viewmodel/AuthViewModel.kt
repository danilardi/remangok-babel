package com.ipb.remangokbabel.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.ipb.remangokbabel.data.repository.Repository
import com.ipb.remangokbabel.model.request.LoginRequest
import com.ipb.remangokbabel.model.request.RegisterRequest
import com.ipb.remangokbabel.model.response.LoginResponse
import com.ipb.remangokbabel.model.response.RegisterResponse
import com.ipb.remangokbabel.utils.handleException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: Repository) : BaseViewModel() {

    private val _loginState : MutableStateFlow<LoginResponse?> = MutableStateFlow(null as LoginResponse?)
    private val _registerResponse : MutableStateFlow<RegisterResponse?> = MutableStateFlow(null as RegisterResponse?)

    val loginState : StateFlow<LoginResponse?>
        get() = _loginState
    val registerResponse : StateFlow<RegisterResponse?>
        get() = _registerResponse

    fun login(data: LoginRequest) {
        viewModelScope.launch {
            setLoading(true)
            try {
                _loginState.value = repository.login(data)
            } catch (e: Exception) {
                setError(handleException(e))
            }
            setLoading(false)
        }
    }

    fun register(data: RegisterRequest) {
        viewModelScope.launch {
            setLoading(true)
            try {
                _registerResponse.value = repository.register(data)
            } catch (e: Exception) {
                setError(handleException(e))
            }
            setLoading(false)
        }
    }
}