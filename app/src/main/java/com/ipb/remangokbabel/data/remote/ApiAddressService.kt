package com.ipb.remangokbabel.data.remote

import com.ipb.remangokbabel.model.response.GetKabupatenKotaResponseItem
import com.ipb.remangokbabel.model.response.GetKecamatanResponseItem
import com.ipb.remangokbabel.model.response.GetKelurahanResponseItem
import com.ipb.remangokbabel.model.response.GetProvinsiResponseItem
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiAddressService {
    @GET("provinces.json")
    suspend fun getProvinsi(): List<GetProvinsiResponseItem>

    @GET("regencies/{id}.json")
    suspend fun getKabupatenKota(@Path("id") id: String): List<GetKabupatenKotaResponseItem>

    @GET("districts/{id}.json")
    suspend fun getKecamatan(@Path("id") id: String): List<GetKecamatanResponseItem>

    @GET("villages/{id}.json")
    suspend fun getKelurahan(@Path("id") id: String): List<GetKelurahanResponseItem>
}