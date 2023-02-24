package com.yapp.buddycon.data.network.response

import com.google.gson.annotations.SerializedName
import com.yapp.buddycon.domain.model.ChangeCouponResult

data class ChangeCouponResponse(
    @SerializedName("message")
    val message: String = "",
    @SerializedName("success")
    val success: Boolean = false
) {
    fun toModel() = ChangeCouponResult(
        message = message,
        success = success
    )
}
