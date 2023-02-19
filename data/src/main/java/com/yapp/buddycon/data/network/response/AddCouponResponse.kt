package com.yapp.buddycon.data.network.response

import com.google.gson.annotations.SerializedName
import com.yapp.buddycon.domain.model.AddCouponResult

data class AddCouponResponse(
    @SerializedName("success")
    val isSuccess: Boolean = false,
    @SerializedName("message")
    val message: String = "",
) {
    fun mapToAddCouponResult() = AddCouponResult(
        isSuccess = isSuccess,
        resultMessage = message
    )
}
