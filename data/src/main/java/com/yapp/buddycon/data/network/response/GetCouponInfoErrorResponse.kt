package com.yapp.buddycon.data.network.response

import com.google.gson.annotations.SerializedName

data class GetCouponInfoErrorResponse(
    @SerializedName("code")
    val code: String = ""
)
