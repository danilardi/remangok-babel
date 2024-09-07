package com.ipb.remangokbabel.data.remote

import com.ipb.remangokbabel.model.request.LoginRequest
import com.ipb.remangokbabel.model.request.RegisterRequest
import com.ipb.remangokbabel.model.request.UploadProductRequest
import com.ipb.remangokbabel.model.response.LoginResponse
import com.ipb.remangokbabel.model.response.ProductResponse
import com.ipb.remangokbabel.model.response.RegisterResponse
import com.ipb.remangokbabel.model.response.StatusMessageResponse
import com.ipb.remangokbabel.model.response.UploadImageResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("authentications")
    suspend fun login(
        @Body data: LoginRequest
    ): LoginResponse

    @DELETE("authentications")
    suspend fun logout(): StatusMessageResponse

    @POST("users")
    suspend fun register(
        @Body data: RegisterRequest
    ): RegisterResponse

    @GET("products")
    suspend fun getAllProducts(
        @Query ("limit") limit: Int,
        @Query ("offset") offset: Int
    ): ProductResponse

    @Multipart
    @POST("products/upload")
    suspend fun uploadImage(
        @Part gambar: MultipartBody.Part,
    ): UploadImageResponse

    @DELETE("public/products/images/{filename}")
    suspend fun deleteImage(
        @Path("filename") filename: String
    ): StatusMessageResponse

    @POST("products")
    suspend fun uploadProduct(
        @Body data: UploadProductRequest
    ): StatusMessageResponse
}