package com.ipb.remangokbabel.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ipb.remangokbabel.data.repository.Repository
import com.ipb.remangokbabel.model.request.UploadProductRequest
import com.ipb.remangokbabel.model.response.ErrorResponse
import com.ipb.remangokbabel.model.response.ProductResponse
import com.ipb.remangokbabel.model.response.StatusMessageResponse
import com.ipb.remangokbabel.model.response.UploadImageResponse
import com.ipb.remangokbabel.utils.handleException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.io.File

class ProductViewModel(private val repository: Repository) : ViewModel() {

    private val _showLoading = MutableSharedFlow<Boolean>()
    private val _errorResponse = MutableSharedFlow<ErrorResponse>()
    val showLoading: SharedFlow<Boolean>
        get() = _showLoading
    val errorResponse: SharedFlow<ErrorResponse>
        get() = _errorResponse

    private val _uploadImageResponse = MutableSharedFlow<UploadImageResponse>()
    private val _deleteImageResponse = MutableSharedFlow<StatusMessageResponse>()
    private val _uploadProductResponse = MutableSharedFlow<StatusMessageResponse>()
    private val _getAllProductsResponse = MutableSharedFlow<ProductResponse>()

    val uploadImageResponse: SharedFlow<UploadImageResponse>
        get() = _uploadImageResponse
    val deleteImageResponse: SharedFlow<StatusMessageResponse>
        get() = _deleteImageResponse
    val uploadProductResponse: SharedFlow<StatusMessageResponse>
        get() = _uploadProductResponse
    val getAllProductsResponse: SharedFlow<ProductResponse>
        get() = _getAllProductsResponse

    fun uploadImage(image: File) {
        viewModelScope.launch {
            _showLoading.emit(true)
            try {
                _uploadImageResponse.emit(repository.uploadImage(image))
            } catch (e: Exception) {
                _errorResponse.emit(handleException(e))
            }
        }
    }

    fun deleteImage(filename: String) {
        viewModelScope.launch {
            _showLoading.emit(true)
            try {
                _deleteImageResponse.emit(repository.deleteImage(filename))
            } catch (e: Exception) {
                _errorResponse.emit(handleException(e))
            }
            _showLoading.emit(false)
        }
    }

    fun uploadProduct(data: UploadProductRequest) {
        viewModelScope.launch {
            _showLoading.emit(true)
            try {
                _uploadProductResponse.emit(repository.uploadProduct(data))
            } catch (e: Exception) {
                _errorResponse.emit(handleException(e))
            }
            _showLoading.emit(false)
        }
    }

    fun getAllProducts(limit: Int, offset: Int) {
        viewModelScope.launch {
            _showLoading.emit(true)
            try {
                _getAllProductsResponse.emit(repository.getAllProducts(limit, offset))
            } catch (e: Exception) {
                _errorResponse.emit(handleException(e))
            }
            _showLoading.emit(false)
        }
    }

    fun showLoading(showLoading: Boolean) {
        viewModelScope.launch {
            _showLoading.emit(showLoading)
        }
    }
}