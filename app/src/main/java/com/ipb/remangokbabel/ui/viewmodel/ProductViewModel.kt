package com.ipb.remangokbabel.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.ipb.remangokbabel.data.repository.Repository
import com.ipb.remangokbabel.model.request.UploadProductRequest
import com.ipb.remangokbabel.model.request.VerifyProductRequest
import com.ipb.remangokbabel.model.response.GetAllProductResponse
import com.ipb.remangokbabel.model.response.StatusMessageResponse
import com.ipb.remangokbabel.model.response.UploadImageResponse
import com.ipb.remangokbabel.utils.handleException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File

class ProductViewModel(private val repository: Repository) : BaseViewModel() {

    val uploadImageResponse = MutableSharedFlow<UploadImageResponse>()
    val deleteImageResponse = MutableSharedFlow<StatusMessageResponse>()
    val uploadProductResponse = MutableSharedFlow<StatusMessageResponse>()
    val getAllProductsResponse = MutableSharedFlow<GetAllProductResponse>()
    val deleteProductResponse = MutableSharedFlow<StatusMessageResponse>()

    private val _productStateAll: MutableStateFlow<GetAllProductResponse?> =
        MutableStateFlow(null as GetAllProductResponse?)
    private val _productStateRequested: MutableStateFlow<GetAllProductResponse?> =
        MutableStateFlow(null as GetAllProductResponse?)
    private val _productStateRejected: MutableStateFlow<GetAllProductResponse?> =
        MutableStateFlow(null as GetAllProductResponse?)
    private val _productStateAccepted: MutableStateFlow<GetAllProductResponse?> =
        MutableStateFlow(null as GetAllProductResponse?)
    private val _uploadImageState: MutableStateFlow<UploadImageResponse?> =
        MutableStateFlow(null as UploadImageResponse?)
    private val _deleteImageState: MutableStateFlow<StatusMessageResponse?> =
        MutableStateFlow(null as StatusMessageResponse?)
    private val _uploadProductState: MutableStateFlow<StatusMessageResponse?> =
        MutableStateFlow(null as StatusMessageResponse?)
    private val _deleteProductState: MutableStateFlow<StatusMessageResponse?> =
        MutableStateFlow(null as StatusMessageResponse?)
    private val _verifyProductState: MutableStateFlow<StatusMessageResponse?> =
        MutableStateFlow(null as StatusMessageResponse?)

    val productStateAll: StateFlow<GetAllProductResponse?>
        get() = _productStateAll
    val productStateRequested: StateFlow<GetAllProductResponse?>
        get() = _productStateRequested
    val productStateAccepted: StateFlow<GetAllProductResponse?>
        get() = _productStateAccepted
    val productStateRejected: StateFlow<GetAllProductResponse?>
        get() = _productStateRejected
    val uploadImageState: StateFlow<UploadImageResponse?>
        get() = _uploadImageState
    val deleteImageState: StateFlow<StatusMessageResponse?>
        get() = _deleteImageState
    val uploadProductState: StateFlow<StatusMessageResponse?>
        get() = _uploadProductState
    val deleteProductState: StateFlow<StatusMessageResponse?>
        get() = _deleteProductState
    val verifyProductState: StateFlow<StatusMessageResponse?>
        get() = _verifyProductState


    fun getAllProducts(limit: Int = 10, offset: Int = 0, kecamatan: String? = null) {
        viewModelScope.launch {
            setLoading(true)
            try {
                if (kecamatan?.isEmpty() == true) {
                    println("masuk sini")
                    _productStateAll.value = repository.getAllProducts(limit, offset, null)
                } else {
                    println("masuk sini2 $kecamatan")
                    _productStateAll.value = repository.getAllProducts(limit, offset, kecamatan)
                }
            } catch (e: Exception) {
                setError(handleException(e))
            }
            setLoading(false)
        }
    }

    fun clearProductStateALl() {
        _productStateAll.value = null
    }

    fun getSelfProducts(limit: Int = 10, offset: Int = 0, status: String) {
        viewModelScope.launch {
            setLoading(true)
            try {
                when (status) {
                    "requested" -> {
                        _productStateRequested.value = repository.getSelfProducts(limit, offset, status)
                    }
                    "accepted" -> {
                        _productStateAccepted.value = repository.getSelfProducts(limit, offset, status)
                    }
                    "rejected" -> {
                        _productStateRejected.value = repository.getSelfProducts(limit, offset, status)
                    }
                }
            } catch (e: Exception) {
                setError(handleException(e))
            }
            setLoading(false)
        }
    }

    fun getAllProductsByAdmin(limit: Int = 10, offset: Int = 0, status: String) {
        viewModelScope.launch {
            setLoading(true)
            try {
                when (status) {
                    "requested" -> {
                        _productStateRequested.value = repository.getAllProductsByAdmin(limit, offset, status)
                    }
                    "accepted" -> {
                        _productStateAccepted.value = repository.getAllProductsByAdmin(limit, offset, status)
                    }
                    "rejected" -> {
                        _productStateRejected.value = repository.getAllProductsByAdmin(limit, offset, status)
                    }
                }
            } catch (e: Exception) {
                setError(handleException(e))
            }
            setLoading(false)
        }
    }

    fun clearProductStateRequested() {
        _productStateRequested.value = null
    }

    fun clearProductStateAccepted() {
        _productStateAccepted.value = null
    }

    fun clearProductStateRejected() {
        _productStateRejected.value = null
    }

    fun uploadImage(image: File) {
        viewModelScope.launch {
            setLoading(true)
            try {
                _uploadImageState.value = repository.uploadImage(image)
            } catch (e: Exception) {
                setError(handleException(e))
            }
            setLoading(false)
        }
    }

    fun clearUploadImageState() {
        _uploadImageState.value = null
    }

    fun deleteImage(filename: String) {
        viewModelScope.launch {
            setLoading(true)
            try {
                _deleteImageState.value = repository.deleteImage(filename)
            } catch (e: Exception) {
                setError(handleException(e))
            }
            setLoading(false)
        }
    }

    fun clearDeleteImageState() {
        _deleteImageState.value = null
    }

    fun uploadProduct(data: UploadProductRequest) {
        viewModelScope.launch {
            setLoading(true)
            try {
                _uploadProductState.value = repository.uploadProduct(data)
            } catch (e: Exception) {
                setError(handleException(e))
            }
            setLoading(false)
        }
    }

    fun updateProduct(id: Int, data: UploadProductRequest) {
        viewModelScope.launch {
            setLoading(true)
            try {
                _uploadProductState.value = repository.updateProduct(id, data)
            } catch (e: Exception) {
                setError(handleException(e))
            }
            setLoading(false)
        }
    }

    fun clearUploadProductState() {
        _uploadProductState.value = null
    }

    fun deleteProduct(id: Int) {
        viewModelScope.launch {
            setLoading(true)
            try {
                _deleteProductState.value = repository.deleteProduct(id)
            } catch (e: Exception) {
                setError(handleException(e))
            }
            setLoading(false)
        }
    }

    fun clearDeleteProductState() {
        _deleteProductState.value = null
    }

    fun verifyProduct(data: VerifyProductRequest) {
        viewModelScope.launch {
            setLoading(true)
            try {
                _verifyProductState.value = repository.verifyProduct(data)
            } catch (e: Exception) {
                setError(handleException(e))
            }
            setLoading(false)
        }
    }

    fun clearVerifyProductState() {
        _verifyProductState.value = null
    }
}