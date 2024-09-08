package com.ipb.remangokbabel.model.request

import com.google.gson.annotations.SerializedName

data class UploadProductRequest(

    @field:SerializedName("jumlah_stok")
    val jumlahStok: Int,

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("harga_satuan")
    val hargaSatuan: Int,

    @field:SerializedName("berat")
    val berat: Int,

    @field:SerializedName("fase_hidup")
    val faseHidup: String,

    @field:SerializedName("deskripsi")
    val deskripsi: String,

    @field:SerializedName("gambar")
    val gambar: List<String>
)
