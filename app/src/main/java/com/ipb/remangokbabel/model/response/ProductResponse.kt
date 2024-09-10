package com.ipb.remangokbabel.model.response

import com.google.gson.annotations.SerializedName


data class GetDetailProductResponse(

    @field:SerializedName("data")
    val detailProductData: DetailProductData,

    @field:SerializedName("status")
    val status: String
)

data class DetailProduk(

    @field:SerializedName("jumlah_stok")
    val jumlahStok: Int,

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("harga_satuan")
    val hargaSatuan: Int,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("berat")
    val berat: Int,

    @field:SerializedName("fase_hidup")
    val faseHidup: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("deskripsi")
    val deskripsi: String,

    @field:SerializedName("gambar")
    val gambar: List<String>,

    @field:SerializedName("data_penjual")
    val dataPenjual: DataPenjualProduct
)

data class DetailProductData(

    @field:SerializedName("produk")
    val detailProduk: DetailProduk
)

data class DataPenjualProduct(

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("nomor_telepon")
    val nomorTelepon: String,

    @field:SerializedName("email")
    val email: String
)



data class GetAllProductResponse(

    @field:SerializedName("data")
    val data: DataProduct,

    @field:SerializedName("status")
    val status: String
)

data class ProdukItem(

    @field:SerializedName("jumlah_stok")
    val jumlahStok: Int,

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("harga_satuan")
    val hargaSatuan: Int,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("id_owner")
    val idOwner: String,

    @field:SerializedName("berat")
    val berat: Int,

    @field:SerializedName("fase_hidup")
    val faseHidup: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("deskripsi")
    val deskripsi: String,

    @field:SerializedName("gambar")
    val gambar: List<String>
)

data class DataProduct(

    @field:SerializedName("produk")
    val produk: List<ProdukItem>
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