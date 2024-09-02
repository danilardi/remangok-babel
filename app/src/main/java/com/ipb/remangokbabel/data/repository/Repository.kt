package com.ipb.remangokbabel.data.repository

import androidx.compose.runtime.Composable
import com.ipb.remangokbabel.data.remote.ApiConfig
import com.ipb.remangokbabel.data.remote.ApiService
import com.ipb.remangokbabel.model.request.LoginRequest
import com.ipb.remangokbabel.model.response.LoginResponse
import com.ipb.remangokbabel.model.response.LogoutResponse

class Repository(private val apiService: ApiService) {
    suspend fun login(data: LoginRequest): LoginResponse {
        return apiService.login(data)
    }

    suspend fun logout(): LogoutResponse {
        return apiService.logout()
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