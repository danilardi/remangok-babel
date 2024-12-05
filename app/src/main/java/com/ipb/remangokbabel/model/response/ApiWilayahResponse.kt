package com.ipb.remangokbabel.model.response

import com.google.gson.annotations.SerializedName

data class GetProvinsiResponseItem(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: String
)

data class GetKabupatenKotaResponseItem(

    @field:SerializedName("province_id")
    val provinceId: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: String
)

data class GetKecamatanResponseItem(

    @field:SerializedName("regency_id")
    val regencyId: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: String
)

data class GetKelurahanResponseItem(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("district_id")
    val districtId: String
)