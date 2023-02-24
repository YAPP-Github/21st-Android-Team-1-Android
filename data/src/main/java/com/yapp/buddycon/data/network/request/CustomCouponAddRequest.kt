package com.yapp.buddycon.data.network.request

data class CustomCouponAddRequest(
    val sharedCouponId: Long? = null,
    val barcode: String = "",
    val name: String = "",
    val expireDate: String = "",
    val storeName: String = "",
    val memo: String = "",
    val sentMemberName: String = ""
)
