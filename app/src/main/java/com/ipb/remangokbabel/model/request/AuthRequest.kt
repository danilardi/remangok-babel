package com.ipb.remangokbabel.model.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val email: String,
    val password: String,
)

data class RegisterRequest(
    val fullname: String,
    val email: String,
    val nomorTelepon: String,
    val password: String,
    val role: String = "user",
    val profile: ProfileRequest
)

data class ProfileRequest(
    val nik: String,
    val kotaKabupaten: String = "Kabupaten Bangka Tengah",
    val kecamatan: String,
    val kelurahan: String,
    val alamat: String,
    val kodePos: String,
)

data class RefreshTokenRequest(
    @field:SerializedName("refresh_token")
    val refreshToken: String
)

