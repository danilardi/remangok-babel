package com.ipb.remangokbabel.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.ipb.remangokbabel.model.response.ErrorResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BaseViewModel : ViewModel(){
    private val _showLoading : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _errorResponse : MutableStateFlow<ErrorResponse> = MutableStateFlow(ErrorResponse())

    val showLoading : StateFlow<Boolean>
        get() = _showLoading
    val errorResponse : StateFlow<ErrorResponse>
        get() = _errorResponse

    fun setLoading(isLoading: Boolean){
        _showLoading.value = isLoading
    }

    fun setError(error: ErrorResponse){
        _errorResponse.value = error
    }

    fun clearError(){
        _errorResponse.value = ErrorResponse()
    }
}