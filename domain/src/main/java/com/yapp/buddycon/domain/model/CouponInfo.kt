package com.yapp.buddycon.domain.model

data class CouponInfo(
    val id: Int,
    val imageUrl: String,
    val barcode: String,
    val name: String,
    val expireDate: String,
    val storeName: String,
    val memo: String
)
