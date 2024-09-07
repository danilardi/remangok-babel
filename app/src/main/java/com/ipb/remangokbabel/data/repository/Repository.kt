package com.ipb.remangokbabel.data.repository

import androidx.compose.runtime.Composable
import com.ipb.remangokbabel.data.remote.ApiConfig
import com.ipb.remangokbabel.data.remote.ApiService
import com.ipb.remangokbabel.model.request.LoginRequest
import com.ipb.remangokbabel.model.request.RegisterRequest
import com.ipb.remangokbabel.model.request.UploadProductRequest
import com.ipb.remangokbabel.model.response.GetAllProductResponse
import com.ipb.remangokbabel.model.response.GetDetailProductResponse
import com.ipb.remangokbabel.model.response.LoginResponse
import com.ipb.remangokbabel.model.response.RegisterResponse
import com.ipb.remangokbabel.model.response.StatusMessageResponse
import com.ipb.remangokbabel.model.response.UploadImageResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class Repository(private val apiService: ApiService) {
    suspend fun login(data: LoginRequest): LoginResponse {
        return apiService.login(data)
    }

    suspend fun register(data: RegisterRequest): RegisterResponse {
        return apiService.register(data)
    }

    suspend fun logout(): StatusMessageResponse {
        return apiService.logout()
    }

    suspend fun getAllProducts(limit: Int, offset: Int): GetAllProductResponse {
        return apiService.getAllProducts(limit, offset)
    }

    suspend fun getProduct(id: Int): GetDetailProductResponse {
        return apiService.getProduct(id)
    }

    suspend fun uploadImage(image: File): UploadImageResponse {
        val requestImageFile = image.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "gambar",
            image.name,
            requestImageFile
        )
        return apiService.uploadImage(multipartBody)
    }

    suspend fun deleteImage(filename: String): StatusMessageResponse {
        return apiService.deleteImage(filename)
    }

    suspend fun uploadProduct(data: UploadProductRequest): StatusMessageResponse {
        return apiService.uploadProduct(data)
    }

    suspend fun updateProduct(id: Int, data: UploadProductRequest): StatusMessageResponse {
        return apiService.updateProduct(id, data)
    }

    suspend fun deleteProduct(id: Int): StatusMessageResponse {
        return apiService.deleteProduct(id)
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        @Composable
        fun getInstance(): Repository =
            instance ?: synchronized(this) {
                val apiService = ApiConfig.getApiService()
                Repository(apiService).apply {
                    instance = this
                }
            }
    }
}