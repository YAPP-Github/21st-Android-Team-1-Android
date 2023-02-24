package com.yapp.buddycon.data.network.request


import com.google.gson.annotations.SerializedName

data class UpdateCouponRequest(
    @SerializedName("expireDate")
    val expireDate: String = "",
    @SerializedName("isMoneyCoupon")
    val isMoneyCoupon: Boolean = false,
    @SerializedName("leftMoney")
    val leftMoney: Int,
    @SerializedName("memo")
    val memo: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("storeName")
    val storeName: String = ""
)