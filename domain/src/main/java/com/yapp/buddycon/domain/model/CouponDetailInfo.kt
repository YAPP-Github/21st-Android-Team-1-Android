package com.yapp.buddycon.domain.model

data class CouponDetailInfo(
    val id: Int = -1,
    val imageUrl: String = "",
    val barcode: String = "",
    val name: String = "",
    val expireDate: String = "",
    val storeName: String = "",
    val memo: String = "",
)
