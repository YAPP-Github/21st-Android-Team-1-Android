package com.yapp.buddycon.data.network.request


import com.google.gson.annotations.SerializedName

data class ChangeCouponRequest(
    @SerializedName("state")
    val state: String = ""
)