package com.yapp.buddycon.data.network.response

import com.yapp.buddycon.domain.model.GiftConDetail

data class GiftConDetailResponse(
    val barcode: String,
    val expireDate: String,
    val id: Int,
    val imageUrl: String,
    val isMoneyCoupon: Boolean,
    val leftMoney: Int?,
    val memo: String,
    val name: String,
    val storeName: String
){
    fun toModel() = GiftConDetail(
        id = id,
        barcode = barcode,
        imageUrl =  imageUrl,
        isMoneyCoupon =  isMoneyCoupon,
        expireDate = expireDate,
        leftMoney = leftMoney?:0,
        name = name,
        memo =  memo,
        storeName = storeName
    )
}