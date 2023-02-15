package com.yapp.buddycon.data.network.request

import java.time.LocalDate

data class CustomCouponAddRequest(
    val sharedCouponId: Long? = null,
    val barcode: String = "",
    val name: String = "",
    val expireDate: LocalDate = LocalDate.of(2023, 1, 1),
    val storeName: String = "",
    val memo: String = "",
    val sentMemberName: String = ""
)
