package com.ipb.remangokbabel.model.response

import com.google.gson.annotations.SerializedName

data class GetAllProductResponse(

    @field:SerializedName("data")
    val data: DataProduct,

    @field:SerializedName("status")
    val status: String
)

data class DataProduct(

    @field:SerializedName("produk")
    val produk: List<ProductItem>
)

data class ProductItem(

    @field:SerializedName("dataPemilik")
    val dataPemilik: DataPemilik,

    @field:SerializedName("dataAdmin")
    val dataAdmin: DataAdmin,

    @field:SerializedName("gambar")
    val gambar: List<String>,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("hargaSatuan")
    val hargaSatuan: Int,

    @field:SerializedName("berat")
    val berat: Int,

    @field:SerializedName("jumlahStok")
    val jumlahStok: Int,

    @field:SerializedName("tipeStok")
    val unit: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("deskripsi")
    val deskripsi: String,

    @field:SerializedName("tipe")
    val tipe: String,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("alasanPenolakan")
    val alasanPenolakan: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String
)

data class DataPemilik(

    @field:SerializedName("role")
    val role: String,

    @field:SerializedName("profile")
    val profile: Profile,

    @field:SerializedName("nomorTelepon")
    val nomorTelepon: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("fullname")
    val fullname: String,

    @field:SerializedName("email")
    val email: String
)

data class DataAdmin(

    @field:SerializedName("role")
    val role: String,

    @field:SerializedName("profile")
    val profile: Profile,

    @field:SerializedName("nomorTelepon")
    val nomorTelepon: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("fullname")
    val fullname: String,

    @field:SerializedName("email")
    val email: String
)

data class Profile(

    @field:SerializedName("kecamatan")
    val kecamatan: String,

    @field:SerializedName("kodePos")
    val kodePos: String,

    @field:SerializedName("kotaKabupaten")
    val kotaKabupaten: String,

    @field:SerializedName("kelurahan")
    val kelurahan: String,

    @field:SerializedName("alamat")
    val alamat: String
)

data class UploadImageResponse(

    @field:SerializedName("data")
    val data: UploadImageData,

    @field:SerializedName("status")
    val status: String
)

data class UploadImageData(

    @field:SerializedName("filename")
    val filename: String
)