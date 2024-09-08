package com.ipb.remangokbabel.model.request

import com.google.gson.annotations.SerializedName

data class AddOrderRequest(
    @field:SerializedName("nama_kelurahan")
    val namaKelurahan: String,

    @field:SerializedName("id_produk")
    val idProduk: Int,

    @field:SerializedName("nama_kecamatan")
    val namaKecamatan: String,

    @field:SerializedName("nama_depan")
    val namaDepan: String,

    @field:SerializedName("nama_provinsi")
    val namaProvinsi: String,

    @field:SerializedName("jumlah_pesanan")
    val jumlahPesanan: Int,

    @field:SerializedName("nama_kota_kabupaten")
    val namaKotaKabupaten: String,

    @field:SerializedName("nomor_telepon")
    val nomorTelepon: String,

    @field:SerializedName("kode_pos")
    val kodePos: String,

    @field:SerializedName("nama_belakang")
    val namaBelakang: String,

    @field:SerializedName("alamat")
    val alamat: String,

    @field:SerializedName("status")
    val status: String,
)