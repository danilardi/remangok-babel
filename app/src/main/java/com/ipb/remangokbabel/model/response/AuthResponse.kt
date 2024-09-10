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


data class RegisterResponse(

    @field:SerializedName("data")
    val data: DataRegister,

    @field:SerializedName("status")
    val status: String
)

data class AddedUser(

    @field:SerializedName("role")
    val role: String,

    @field:SerializedName("nomor_telepon")
    val nomorTelepon: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("fullname")
    val fullname: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("username")
    val username: String
)

data class DataRegister(

    @field:SerializedName("addedUser")
    val addedUser: AddedUser
)

data class RefreshTokenResponse(

    @field:SerializedName("data")
    val data: TokenData,

    @field:SerializedName("status")
    val status: String
)

data class TokenData(

    @field:SerializedName("access_token")
    val accessToken: String
)
