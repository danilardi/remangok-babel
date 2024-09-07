package com.ipb.remangokbabel.model.response

import com.google.gson.annotations.SerializedName

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
