package com.ipb.remangokbabel.model.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class ProdukItem(

	@field:SerializedName("jumlah_stok")
	val jumlahStok: Int? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("harga_satuan")
	val hargaSatuan: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("id_owner")
	val idOwner: String? = null,

	@field:SerializedName("berat")
	val berat: Int? = null,

	@field:SerializedName("fase_hidup")
	val faseHidup: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("gambar")
	val gambar: List<String?>? = null
)

data class Data(

	@field:SerializedName("produk")
	val produk: List<ProdukItem?>? = null
)
