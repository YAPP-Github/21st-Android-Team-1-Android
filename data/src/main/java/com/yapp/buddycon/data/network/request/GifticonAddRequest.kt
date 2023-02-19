package com.yapp.buddycon.data.network.request

import java.time.LocalDate

data class GifticonAddRequest(
    val sharedCouponId: Long? = null,
    val barcode: String = "",
    val name: String = "",
    val expireDate: String = "",
    val storeName: String = "",
    val memo: String = "",
    val isMoneyCoupon: Boolean = false,
    val leftMoney: Int = 0
)
