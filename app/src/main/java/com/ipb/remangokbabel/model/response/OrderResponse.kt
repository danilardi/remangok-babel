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

    @field:SerializedName("harga_satuan")
    val hargaSatuan: Int,

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
