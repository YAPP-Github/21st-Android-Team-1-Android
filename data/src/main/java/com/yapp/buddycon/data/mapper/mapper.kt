package com.yapp.buddycon.data.mapper

import com.yapp.buddycon.data.network.request.CustomCouponAddRequest
import com.yapp.buddycon.data.network.request.GifticonAddRequest
import com.yapp.buddycon.data.network.response.UserInfoResponse
import com.yapp.buddycon.domain.model.CouponInputInfo
import com.yapp.buddycon.domain.model.UserInfo
import java.time.LocalDate

fun mapCouponInputInfoToCustomCouponAddRequest(couponInputInfo: CouponInputInfo): CustomCouponAddRequest {
    val dateList = couponInputInfo.expireDate.split('-').map { it.toInt() }

    return CustomCouponAddRequest(
        sharedCouponId = if (couponInputInfo.id >= 1) couponInputInfo.id else null,
        barcode = couponInputInfo.barcode,
        name = couponInputInfo.name,
        expireDate = LocalDate.of(dateList[0], dateList[1], dateList[2]),
        storeName = couponInputInfo.storeName,
        memo = couponInputInfo.memo,
        sentMemberName = couponInputInfo.sentMemberName
    )
}

fun mapCouponInputInfoToGifticonAddRequest(couponInputInfo: CouponInputInfo): GifticonAddRequest {
    val dateList = couponInputInfo.expireDate.split('-').map { it.toInt() }

    return GifticonAddRequest(
        sharedCouponId = if (couponInputInfo.id >= 1) couponInputInfo.id else null,
        barcode = couponInputInfo.barcode,
        name = couponInputInfo.name,
        expireDate = LocalDate.of(dateList[0], dateList[1], dateList[2]),
        storeName = couponInputInfo.storeName,
        memo = couponInputInfo.memo,
    )
}