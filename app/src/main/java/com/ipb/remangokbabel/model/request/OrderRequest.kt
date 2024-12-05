package com.ipb.remangokbabel.model.request

import com.google.gson.annotations.SerializedName

data class AddOrderRequest(
    @field:SerializedName("id_produk")
    val idProduk: Int,

    @field:SerializedName("id_profile")
    val idProfile: String,

    @field:SerializedName("jumlah_pesanan")
    val jumlahPesanan: Int,
)

data class EditOrderRequest(
    @field:SerializedName("id_profile")
    val idProfile: String,

    @field:SerializedName("jumlah_pesanan")
    val jumlahPesanan: Int,
)

data class UpdateTransactionRequest(
    @field:SerializedName("id_order")
    val idOrder: String,

    @field:SerializedName("status")
    val status: String,
)