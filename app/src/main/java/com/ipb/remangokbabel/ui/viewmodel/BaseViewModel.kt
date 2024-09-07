package com.ipb.remangokbabel.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.ipb.remangokbabel.model.response.ErrorResponse
import kotlinx.coroutines.flow.MutableSharedFlow

open class BaseViewModel : ViewModel(){
    val showLoading = MutableSharedFlow<Boolean>()
    val errorResponse = MutableSharedFlow<ErrorResponse>()
}