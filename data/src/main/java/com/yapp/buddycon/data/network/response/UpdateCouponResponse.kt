package com.yapp.buddycon.data.network.response


import com.google.gson.annotations.SerializedName
import com.yapp.buddycon.domain.model.UpdateCouponResult

data class UpdateCouponResponse(
    @SerializedName("message")
    val message: String = "",
    @SerializedName("success")
    val success: Boolean = false
){
    fun toModel() = UpdateCouponResult(
        message = message,
        success = success
    )
}