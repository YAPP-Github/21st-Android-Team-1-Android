package com.yapp.buddycon.data.network.response


import com.google.gson.annotations.SerializedName
import com.yapp.buddycon.data.db.entity.CouponEntity

data class CouponResponse(
    @SerializedName("id")
    val id: Int = 0 ,
    @SerializedName("imageUrl")
    val imageUrl: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("expireDate")
    val expireDate: String = "",
    @SerializedName("createdAt")
    val createdAt: String = "",
    @SerializedName("shared")
    val shared: Boolean = true
) {
    fun toEntity(usable: Boolean) = CouponEntity(
        id = id,
        imageUrl = imageUrl,
        name = name,
        expireDate = expireDate,
        createdAt = createdAt,
        usable = usable,
        shared = shared
    )
}