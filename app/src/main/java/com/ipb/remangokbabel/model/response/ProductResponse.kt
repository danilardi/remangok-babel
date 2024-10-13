package com.ipb.remangokbabel.model.response

import com.google.gson.annotations.SerializedName


data class GetDetailProductResponse(

    @field:SerializedName("data")
    val detailProductData: DetailProductData,

    @field:SerializedName("status")
    val status: String
)

data class DetailProduk(

    @field:SerializedName("owner")
    val owner: Owner,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("hargaSatuan")
    val hargaSatuan: Int,

    @field:SerializedName("faseHidup")
    val faseHidup: String,

    @field:SerializedName("berat")
    val berat: Int,

    @field:SerializedName("jumlahStok")
    val jumlahStok: Int,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("deskripsi")
    val deskripsi: String,

    @field:SerializedName("gambar")
    val gambar: List<String>,

    @field:SerializedName("updatedAt")
    val updatedAt: String
)

data class DetailProductData(

    @field:SerializedName("produk")
    val detailProduk: DetailProduk
)

data class GetAllProductResponse(

    @field:SerializedName("data")
    val data: DataProduct,

    @field:SerializedName("status")
    val status: String
)

data class ProdukItem(

    @field:SerializedName("owner")
    val owner: Owner,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("hargaSatuan")
    val hargaSatuan: Int,

    @field:SerializedName("faseHidup")
    val faseHidup: String,

    @field:SerializedName("berat")
    val berat: Int,

    @field:SerializedName("jumlahStok")
    val jumlahStok: Int,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("deskripsi")
    val deskripsi: String,

    @field:SerializedName("gambar")
    val gambar: List<String>,

    @field:SerializedName("updatedAt")
    val updatedAt: String
)

data class DataProduct(

    @field:SerializedName("produk")
    val produk: List<ProdukItem>
)

data class Owner(

    @field:SerializedName("role")
    val role: String,

    @field:SerializedName("profiles")
    val profiles: List<ProfilesItem>,

    @field:SerializedName("nomorTelepon")
    val nomorTelepon: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("fullname")
    val fullname: String,

    @field:SerializedName("email")
    val email: String
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