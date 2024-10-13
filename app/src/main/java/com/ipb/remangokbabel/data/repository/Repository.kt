package com.ipb.remangokbabel.data.repository

import androidx.compose.runtime.Composable
import com.ipb.remangokbabel.data.remote.ApiAddressService
import com.ipb.remangokbabel.data.remote.ApiConfig
import com.ipb.remangokbabel.data.remote.ApiService
import com.ipb.remangokbabel.model.request.AddOrderRequest
import com.ipb.remangokbabel.model.request.AddProfileRequest
import com.ipb.remangokbabel.model.request.LoginRequest
import com.ipb.remangokbabel.model.request.RefreshTokenRequest
import com.ipb.remangokbabel.model.request.RegisterRequest
import com.ipb.remangokbabel.model.request.UpdateTransactionRequest
import com.ipb.remangokbabel.model.request.UploadProductRequest
import com.ipb.remangokbabel.model.response.DetailOrderResponse
import com.ipb.remangokbabel.model.response.GetAllProductResponse
import com.ipb.remangokbabel.model.response.GetDetailProductResponse
import com.ipb.remangokbabel.model.response.GetKabupatenKotaResponseItem
import com.ipb.remangokbabel.model.response.GetKecamatanResponseItem
import com.ipb.remangokbabel.model.response.GetKelurahanResponseItem
import com.ipb.remangokbabel.model.response.GetOrderResponse
import com.ipb.remangokbabel.model.response.GetProfileResponse
import com.ipb.remangokbabel.model.response.GetProvinsiResponseItem
import com.ipb.remangokbabel.model.response.LoginResponse
import com.ipb.remangokbabel.model.response.RefreshTokenResponse
import com.ipb.remangokbabel.model.response.RegisterResponse
import com.ipb.remangokbabel.model.response.StatusMessageResponse
import com.ipb.remangokbabel.model.response.UploadImageResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class Repository(private val apiService: ApiService, private val apiAddressService: ApiAddressService) {
    suspend fun login(data: LoginRequest): LoginResponse {
        return apiService.login(data)
    }

    suspend fun refreshToken(data: RefreshTokenRequest): RefreshTokenResponse {
        return apiService.refreshToken(data)
    }

    suspend fun register(data: RegisterRequest): RegisterResponse {
        return apiService.register(data)
    }

    suspend fun logout(): StatusMessageResponse {
        return apiService.logout()
    }

    suspend fun getAllProducts(limit: Int, offset: Int, isOwner: Boolean = false): GetAllProductResponse {
        return apiService.getAllProducts(limit, offset, isOwner)
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

    suspend fun getProfile(): GetProfileResponse {
        return apiService.getProfile()
    }

    suspend fun addProfile(data: AddProfileRequest): StatusMessageResponse {
        return apiService.addProfile(data)
    }

    suspend fun updateProfile(id: String, data: AddProfileRequest): StatusMessageResponse {
        return apiService.updateProfile(id, data)
    }

    suspend fun deleteProfile(id: String): StatusMessageResponse {
        return apiService.deleteProfile(id)
    }

    suspend fun getProvinsi(): List<GetProvinsiResponseItem> {
        return apiAddressService.getProvinsi()
    }

    suspend fun getKabupatenKota(provinsiId: String): List<GetKabupatenKotaResponseItem> {
        return apiAddressService.getKabupatenKota(provinsiId)
    }

    suspend fun getKecamatan(kabupatenKotaId: String): List<GetKecamatanResponseItem> {
        return apiAddressService.getKecamatan(kabupatenKotaId)
    }

    suspend fun getKelurahan(kecamatanId: String): List<GetKelurahanResponseItem> {
        return apiAddressService.getKelurahan(kecamatanId)
    }

    suspend fun getOrders(): GetOrderResponse {
        return apiService.getOrders()
    }

    suspend fun getDetailOrder(id: String): DetailOrderResponse {
        return apiService.getDetailOrder(id)
    }

    suspend fun createOrder(data: AddOrderRequest): StatusMessageResponse {
        return apiService.createOrder(data)
    }

    suspend fun updateOrder(id: Int, data: AddOrderRequest): StatusMessageResponse {
        return apiService.updateOrder(id, data)
    }

    suspend fun deleteOrder(id: String): StatusMessageResponse {
        return apiService.deleteOrder(id)
    }

    suspend fun updateTransactions(data: UpdateTransactionRequest): StatusMessageResponse {
        return apiService.updateTransactions(data)
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        @Composable
        fun getInstance(): Repository =
            instance ?: synchronized(this) {
                val apiService = ApiConfig.getApiService()
                val apiAddressService = ApiConfig.getApiAddressService()
                Repository(apiService, apiAddressService).apply {
                    instance = this
                }
            }
    }
}