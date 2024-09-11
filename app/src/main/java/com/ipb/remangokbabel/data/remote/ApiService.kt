package com.ipb.remangokbabel.data.remote

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
import com.ipb.remangokbabel.model.response.GetOrderResponse
import com.ipb.remangokbabel.model.response.GetProfileResponse
import com.ipb.remangokbabel.model.response.LoginResponse
import com.ipb.remangokbabel.model.response.RefreshTokenResponse
import com.ipb.remangokbabel.model.response.RegisterResponse
import com.ipb.remangokbabel.model.response.StatusMessageResponse
import com.ipb.remangokbabel.model.response.UploadImageResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("authentications")
    suspend fun login(
        @Body data: LoginRequest
    ): LoginResponse


    @PUT("authentications")
    suspend fun refreshToken(
        @Body data: RefreshTokenRequest
    ): RefreshTokenResponse

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
    ): GetAllProductResponse

    @GET("products/{id}")
    suspend fun getProduct(
        @Path("id") id: Int
    ): GetDetailProductResponse

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

    @PUT("products/{id}")
    suspend fun updateProduct(
        @Path("id") id: Int,
        @Body data: UploadProductRequest
    ): StatusMessageResponse

    @DELETE("products/{id}")
    suspend fun deleteProduct(
        @Path("id") id: Int
    ): StatusMessageResponse

    @GET("orders")
    suspend fun getOrders(): GetOrderResponse

    @GET("orders/{id}")
    suspend fun getDetailOrder(
        @Path("id") id: String
    ): DetailOrderResponse

    @POST("orders")
    suspend fun createOrder(
        @Body data: AddOrderRequest
    ): StatusMessageResponse

    @PUT("orders/{id}")
    suspend fun updateOrder(
        @Path("id") id: Int,
        @Body data: AddOrderRequest
    ): StatusMessageResponse

    @DELETE("orders/{id}")
    suspend fun deleteOrder(
        @Path("id") id: String
    ): StatusMessageResponse

    @GET("profiles")
    suspend fun getProfile(): GetProfileResponse

    @POST("profiles")
    suspend fun addProfile(
        @Body data: AddProfileRequest
    ): StatusMessageResponse

    @PUT("profiles/{id}")
    suspend fun updateProfile(
        @Path("id") id: String,
        @Body data: AddProfileRequest
    ): StatusMessageResponse

    @DELETE("profiles/{id}")
    suspend fun deleteProfile(
        @Path("id") id: String
    ): StatusMessageResponse

    @POST("transactions")
    suspend fun updateTransactions(
        @Body data: UpdateTransactionRequest
    ): StatusMessageResponse

}