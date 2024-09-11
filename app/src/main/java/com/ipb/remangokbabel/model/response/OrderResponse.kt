package com.ipb.remangokbabel.model.response

import com.google.gson.annotations.SerializedName

data class GetOrderResponse(

    @field:SerializedName("data")
    val data: DataOrder,

    @field:SerializedName("status")
    val status: String
)

data class DataOrder(

    @field:SerializedName("ordered")
    val ordered: List<OrderedItem>
)

data class OrderedItem(

    @field:SerializedName("id_produk")
    val idProduk: Int,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("id_penjual")
    val idPenjual: String,

    @field:SerializedName("jumlah_pesanan")
    val jumlahPesanan: Int,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("id_profile")
    val idProfile: String,

    @field:SerializedName("id_pembeli")
    val idPembeli: String,

    @field:SerializedName("status")
    val status: String?
)



data class DetailOrderResponse(

    @field:SerializedName("data")
    val data: DataDetailOrder,

    @field:SerializedName("status")
    val status: String
)

data class DataDetailOrder(

    @field:SerializedName("ordered")
    val ordered: DetailOrderedItem
)

data class DataPenjualOrder(

    @field:SerializedName("nomorTelepon")
    val nomorTelepon: String,

    @field:SerializedName("fullname")
    val fullname: String,

    @field:SerializedName("email")
    val email: String
)

data class DetailOrderedItem(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("data_pembeli")
    val dataPembeli: DataPembeli,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("data_produk")
    val dataProduk: DataProduk,

    @field:SerializedName("jumlah_pesanan")
    val jumlahPesanan: Int,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("data_penjual")
    val dataPenjual: DataPenjualOrder,

    @field:SerializedName("status")
    val status: String?,
)

data class DataPembeli(

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

    @field:SerializedName("namaKotaKabupaten")
    val namaKabupatenKota: String,

    @field:SerializedName("namaKecamatan")
    val namaKecamatan: String,

    @field:SerializedName("alamat")
    val alamat: String
)

data class DataProduk(

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("hargaSatuan")
    val hargaSatuan: Int,

    @field:SerializedName("faseHidup")
    val faseHidup: String,

    @field:SerializedName("berat")
    val berat: Int,

    @field:SerializedName("gambar")
    val gambar: List<String>
)
