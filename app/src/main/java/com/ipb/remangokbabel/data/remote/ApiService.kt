package com.ipb.remangokbabel.data.remote

import com.ipb.remangokbabel.model.request.LoginRequest
import com.ipb.remangokbabel.model.response.LoginResponse
import com.ipb.remangokbabel.model.response.LogoutResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface ApiService {
    @POST("authentications")
    suspend fun login(
        @Body data: LoginRequest
    ): LoginResponse

    @DELETE("authentications")
    suspend fun logout(): LogoutResponse

}