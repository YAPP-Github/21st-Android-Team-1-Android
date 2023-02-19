package com.yapp.buddycon.data.mapper

import com.yapp.buddycon.data.network.request.CustomCouponAddRequest
import com.yapp.buddycon.data.network.request.GifticonAddRequest
import com.yapp.buddycon.domain.model.CouponInputInfo

fun mapCouponInputInfoToCustomCouponAddRequest(couponInputInfo: CouponInputInfo): CustomCouponAddRequest {
    return CustomCouponAddRequest(
        sharedCouponId = if (couponInputInfo.id >= 1) couponInputInfo.id else null,
        barcode = couponInputInfo.barcode,
        name = couponInputInfo.name,
        expireDate = couponInputInfo.expireDate,
        storeName = couponInputInfo.storeName,
        memo = couponInputInfo.memo,
        sentMemberName = couponInputInfo.sentMemberName
    )
}

fun mapCouponInputInfoToGifticonAddRequest(couponInputInfo: CouponInputInfo): GifticonAddRequest {
    return GifticonAddRequest(
        sharedCouponId = if (couponInputInfo.id >= 1) couponInputInfo.id else null,
        barcode = couponInputInfo.barcode,
        name = couponInputInfo.name,
        expireDate = couponInputInfo.expireDate,
        storeName = couponInputInfo.storeName,
        memo = couponInputInfo.memo,
    )
}