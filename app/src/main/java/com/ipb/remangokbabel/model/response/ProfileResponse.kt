package com.ipb.remangokbabel.model.response

import com.google.gson.annotations.SerializedName


data class GetProfileResponse(
    @field:SerializedName("data")
    val data: DataProfile,

    @field:SerializedName("status")
    val status: String
)

data class DataProfile(
    @field:SerializedName("profile")
    val profiles: ProfilesItem
)

data class ProfilesItem(
    @field:SerializedName("nik")
    val nik: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("kecamatan")
    val kecamatan: String,

    @field:SerializedName("kodePos")
    val kodePos: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("kotaKabupaten")
    val kotaKabupaten: String,

    @field:SerializedName("dataDiri")
    val dataDiri: DataDiri,

    @field:SerializedName("kelurahan")
    val kelurahan: String,

    @field:SerializedName("alamat")
    val alamat: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String
)

data class DataDiri(

    @field:SerializedName("role")
    val role: String,

    @field:SerializedName("nomorTelepon")
    val nomorTelepon: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("fullname")
    val fullname: String,

    @field:SerializedName("email")
    val email: String
)
