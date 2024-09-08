package com.ipb.remangokbabel.ui.viewmodel

import com.ipb.remangokbabel.data.repository.Repository
import com.ipb.remangokbabel.model.response.UploadImageResponse
import kotlinx.coroutines.flow.MutableSharedFlow

class OrderViewModel(private val repository: Repository) : BaseViewModel() {

    val uploadImageResponse = MutableSharedFlow<UploadImageResponse>()

//    fun getAllProducts(limit: Int, offset: Int) {
//        viewModelScope.launch {
//            showLoading.emit(true)
//            try {
//                getAllProductsResponse.emit(repository.getAllProducts(limit, offset))
//            } catch (e: Exception) {
//                errorResponse.emit(handleException(e))
//            }
//            showLoading.emit(false)
//        }
//    }
//
//    fun getProduct(id: Int) {
//        viewModelScope.launch {
//            showLoading.emit(true)
//            try {
//                val response = repository.getProduct(id)
//                getProductResponse.emit(response)
//            } catch (e: Exception) {
//                errorResponse.emit(handleException(e))
//            }
//            showLoading.emit(false)
//        }
//    }
}