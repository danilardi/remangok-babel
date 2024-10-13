package com.ipb.remangokbabel.model.response

import com.google.gson.annotations.SerializedName


data class GetProfileResponse(

    @field:SerializedName("data")
    val data: DataProfile,

    @field:SerializedName("status")
    val status: String
)

data class DataProfile(

    @field:SerializedName("profiles")
    val profiles: List<ProfilesItem>
)

data class ProfilesItem(

    @field:SerializedName("namaDepan")
    val namaDepan: String,

    @field:SerializedName("namaKelurahan")
    val namaKelurahan: String,

    @field:SerializedName("namaProvinsi")
    val namaProvinsi: String,

    @field:SerializedName("namaBelakang")
    val namaBelakang: String,

    @field:SerializedName("kodePos")
    val kodePos: String,

    @field:SerializedName("nomorTelepon")
    val nomorTelepon: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("namaKotaKabupaten")
    val namaKotaKabupaten: String,

    @field:SerializedName("namaKecamatan")
    val namaKecamatan: String,

    @field:SerializedName("alamat")
    val alamat: String
)
