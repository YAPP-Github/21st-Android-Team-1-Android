package com.yapp.buddycon.data.network.request


import com.google.gson.annotations.SerializedName

data class UpdateCustomCouponRequest(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("expireDate")
    val expireDate: String = "",
    @SerializedName("storeName")
    val storeName: String = "",
    @SerializedName("sentMemberName")
    val sentMemberName: String = "",
    @SerializedName("memo")
    val memo: String = ""
)