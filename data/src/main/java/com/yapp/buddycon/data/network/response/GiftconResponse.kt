package com.yapp.buddycon.data.network.response


import com.google.gson.annotations.SerializedName

data class GiftconResponse(
    @SerializedName("expireDate")
    val expireDate: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("name")
    val name: String?
)