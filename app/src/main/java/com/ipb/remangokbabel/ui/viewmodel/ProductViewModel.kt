package com.ipb.remangokbabel.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.ipb.remangokbabel.data.repository.Repository
import com.ipb.remangokbabel.model.request.UploadProductRequest
import com.ipb.remangokbabel.model.response.GetAllProductResponse
import com.ipb.remangokbabel.model.response.GetDetailProductResponse
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

    fun getAllProducts(limit: Int, offset: Int, isOwner: Boolean = true) {
        viewModelScope.launch {
            showLoading.emit(true)
            try {
                getAllProductsResponse.emit(repository.getAllProducts(limit, offset, isOwner))
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
}