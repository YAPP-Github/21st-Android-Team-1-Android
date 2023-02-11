package com.yapp.buddycon.domain.model

data class CouponInfo(
    val id: Int,
    val imageUrl: String,
    val name: String,
    val expireDate: String,
    val createdAt: String,
    val usable: Boolean = false,
    val shared: Boolean = false
)