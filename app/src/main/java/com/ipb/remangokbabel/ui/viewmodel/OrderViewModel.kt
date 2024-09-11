package com.ipb.remangokbabel.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.ipb.remangokbabel.data.repository.Repository
import com.ipb.remangokbabel.model.request.AddOrderRequest
import com.ipb.remangokbabel.model.request.UpdateTransactionRequest
import com.ipb.remangokbabel.model.response.DetailOrderResponse
import com.ipb.remangokbabel.model.response.GetOrderResponse
import com.ipb.remangokbabel.model.response.StatusMessageResponse
import com.ipb.remangokbabel.utils.handleException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class OrderViewModel(private val repository: Repository) : BaseViewModel() {
    val getOrdersResponse = MutableSharedFlow<GetOrderResponse>()
    val getDetailOrderResponse = MutableSharedFlow<DetailOrderResponse>()
    val createOrderResponse = MutableSharedFlow<StatusMessageResponse>()
    val updateOrderResponse = MutableSharedFlow<StatusMessageResponse>()
    val deleteOrderResponse = MutableSharedFlow<StatusMessageResponse>()
    val updateTransactionResponse = MutableSharedFlow<StatusMessageResponse>()

    fun getOrders() {
        viewModelScope.launch {
            showLoading.emit(true)
            try {
                val response = repository.getOrders()
                getOrdersResponse.emit(response)
            } catch (e: Exception) {
                errorResponse.emit(handleException(e))
            }
            showLoading.emit(false)
        }
    }

    fun getDetailOrder(id: String) {
        viewModelScope.launch {
            showLoading.emit(true)
            try {
                val response = repository.getDetailOrder(id)
                getDetailOrderResponse.emit(response)
            } catch (e: Exception) {
                errorResponse.emit(handleException(e))
            }
            showLoading.emit(false)
        }
    }

    fun createOrder(data: AddOrderRequest) {
        viewModelScope.launch {
            showLoading.emit(true)
            try {
                val response = repository.createOrder(data)
                createOrderResponse.emit(response)
            } catch (e: Exception) {
                errorResponse.emit(handleException(e))
            }
            showLoading.emit(false)
        }
    }

    fun updateOrder(id: Int, data: AddOrderRequest) {
        viewModelScope.launch {
            showLoading.emit(true)
            try {
                val response = repository.updateOrder(id, data)
                updateOrderResponse.emit(response)
            } catch (e: Exception) {
                errorResponse.emit(handleException(e))
            }
            showLoading.emit(false)
        }
    }

    fun deleteOrder(id: String) {
        viewModelScope.launch {
            showLoading.emit(true)
            try {
                val response = repository.deleteOrder(id)
                deleteOrderResponse.emit(response)
            } catch (e: Exception) {
                errorResponse.emit(handleException(e))
            }
            showLoading.emit(false)
        }
    }

    fun updateTransactions(data: UpdateTransactionRequest) {
        viewModelScope.launch {
            showLoading.emit(true)
            try {
                val response = repository.updateTransactions(data)
                updateTransactionResponse.emit(response)
            } catch (e: Exception) {
                errorResponse.emit(handleException(e))
            }
            showLoading.emit(false)
        }
    }
}