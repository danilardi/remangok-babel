package com.ipb.remangokbabel.model.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("data")
    val dataLoginResponse: DataLoginResponse,

    @field:SerializedName("status")
    val status: String,
)

data class DataLoginResponse(
    @field:SerializedName("access_token")
    val accessToken: String,
    @field:SerializedName("refresh_token")
    val refreshToken: String,
    @field:SerializedName("role")
    val roleId: String,
)




