package com.ipb.remangokbabel.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.ipb.remangokbabel.data.repository.Repository
import com.ipb.remangokbabel.model.request.AddOrderRequest
import com.ipb.remangokbabel.model.request.UpdateTransactionRequest
import com.ipb.remangokbabel.model.request.UploadProductRequest
import com.ipb.remangokbabel.model.response.GetAllProductResponse
import com.ipb.remangokbabel.model.response.GetDetailProductResponse
import com.ipb.remangokbabel.model.response.GetOrderResponse
import com.ipb.remangokbabel.model.response.StatusMessageResponse
import com.ipb.remangokbabel.model.response.UploadImageResponse
import com.ipb.remangokbabel.utils.handleException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.io.File

class ProductViewModel(private val repository: Repository) : BaseViewModel() {

    val uploadImageResponse = MutableSharedFlow<UploadImageResponse>()
    val deleteImageResponse = MutableSharedFlow<StatusMessageResponse>()
    val uploadProductResponse = MutableSharedFlow<StatusMessageResponse>()
    val getAllProductsResponse = MutableSharedFlow<GetAllProductResponse>()
    val getProductResponse = MutableSharedFlow<GetDetailProductResponse>()
    val deleteProductResponse = MutableSharedFlow<StatusMessageResponse>()
    val getOrdersResponse = MutableSharedFlow<GetOrderResponse>()
    val createOrderResponse = MutableSharedFlow<StatusMessageResponse>()
    val updateOrderResponse = MutableSharedFlow<StatusMessageResponse>()
    val deleteOrderResponse = MutableSharedFlow<StatusMessageResponse>()
    val updateTransactionResponse = MutableSharedFlow<StatusMessageResponse>()

    fun getAllProducts(limit: Int, offset: Int) {
        viewModelScope.launch {
            showLoading.emit(true)
            try {
                getAllProductsResponse.emit(repository.getAllProducts(limit, offset))
            } catch (e: Exception) {
                errorResponse.emit(handleException(e))
            }
            showLoading.emit(false)
        }
    }

    fun getProduct(id: Int) {
        viewModelScope.launch {
            showLoading.emit(true)
            try {
                val response = repository.getProduct(id)
                getProductResponse.emit(response)
            } catch (e: Exception) {
                errorResponse.emit(handleException(e))
            }
            showLoading.emit(false)
        }
    }

    fun uploadImage(image: File) {
        viewModelScope.launch {
            showLoading.emit(true)
            try {
                uploadImageResponse.emit(repository.uploadImage(image))
            } catch (e: Exception) {
                errorResponse.emit(handleException(e))
            }
        }
    }

    fun deleteImage(filename: String) {
        viewModelScope.launch {
            showLoading.emit(true)
            try {
                deleteImageResponse.emit(repository.deleteImage(filename))
            } catch (e: Exception) {
                errorResponse.emit(handleException(e))
            }
            showLoading.emit(false)
        }
    }

    fun uploadProduct(data: UploadProductRequest) {
        viewModelScope.launch {
            showLoading.emit(true)
            try {
                uploadProductResponse.emit(repository.uploadProduct(data))
            } catch (e: Exception) {
                errorResponse.emit(handleException(e))
            }
            showLoading.emit(false)
        }
    }

    fun updateProduct(id: Int, data: UploadProductRequest) {
        viewModelScope.launch {
            showLoading.emit(true)
            try {
                uploadProductResponse.emit(repository.updateProduct(id, data))
            } catch (e: Exception) {
                errorResponse.emit(handleException(e))
            }
            showLoading.emit(false)
        }
    }

    fun deleteProduct(id: Int) {
        viewModelScope.launch {
            showLoading.emit(true)
            try {
                deleteProductResponse.emit(repository.deleteProduct(id))
            } catch (e: Exception) {
                errorResponse.emit(handleException(e))
            }
            showLoading.emit(false)
        }
    }

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