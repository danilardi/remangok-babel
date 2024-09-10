package com.ipb.remangokbabel.model.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val username: String,
    val password: String,
)

data class RegisterRequest(
    val password: String,
    val role: String,
    val nomor_telepon: String,
    val fullname: String,
    val email: String,
    val username: String
)

data class RefreshTokenRequest(
    @field:SerializedName("refresh_token")
    val refreshToken: String
)

