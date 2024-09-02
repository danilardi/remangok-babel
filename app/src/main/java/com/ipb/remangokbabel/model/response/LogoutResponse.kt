package com.ipb.remangokbabel.model.response

import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @field:SerializedName("status")
    val status: String,
    @field:SerializedName("message")
    val message: String,
)
