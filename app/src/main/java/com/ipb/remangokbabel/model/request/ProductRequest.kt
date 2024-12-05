package com.ipb.remangokbabel.model.request

import com.google.gson.annotations.SerializedName

data class UploadProductRequest(

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("hargaSatuan")
    val hargaSatuan: Int,

    @field:SerializedName("berat")
    val berat: Int,

    @field:SerializedName("jumlahStok")
    val jumlahStok: Int,

    @field:SerializedName("deskripsi")
    val deskripsi: String,

    @field:SerializedName("tipe")
    val tipe: String,

    @field:SerializedName("gambar")
    val gambar: List<String>
)

data class VerifyProductRequest(

    @field:SerializedName("idProduk")
    val idProduk: String,

    @field:SerializedName("alasanPenolakan")
    val alasanPenolakan: String? = null,

    @field:SerializedName("status")
    val status: String
)
