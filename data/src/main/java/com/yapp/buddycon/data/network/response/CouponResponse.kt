package com.yapp.buddycon.data.network.response


import com.google.gson.annotations.SerializedName

data class CouponResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("expireDate")
    val expireDate: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("shared")
    val shared: Boolean = false
)