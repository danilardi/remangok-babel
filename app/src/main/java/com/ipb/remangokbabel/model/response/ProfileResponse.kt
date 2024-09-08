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

    @field:SerializedName("nama_kelurahan")
    val namaKelurahan: String,

    @field:SerializedName("nama_kecamatan")
    val namaKecamatan: String,

    @field:SerializedName("nama_depan")
    val namaDepan: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("nama_provinsi")
    val namaProvinsi: String,

    @field:SerializedName("nama_kota_kabupaten")
    val namaKotaKabupaten: String,

    @field:SerializedName("nomor_telepon")
    val nomorTelepon: String,

    @field:SerializedName("kode_pos")
    val kodePos: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("nama_belakang")
    val namaBelakang: String,

    @field:SerializedName("alamat")
    val alamat: String
)
