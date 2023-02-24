package com.yapp.buddycon.presentation.ui.common.model

import android.os.Parcelable
import com.yapp.buddycon.domain.model.GiftConDetail
import kotlinx.parcelize.Parcelize

@Parcelize
data class GiftConInfo(
    var id: Int = -1,
    var imageUrl: String = "",
    var barcode: String = "",
    var name: String = "",
    var expireDate: String = "",
    var storeName: String = "",
    var memo: String = "",
    var isMoneyCoupon: Boolean = false,
    var leftMoney: Int = -1,
    val sentMemberName: String? = null
) : Parcelable

fun GiftConDetail.toInfo() = GiftConInfo(
    id = id,
    imageUrl = imageUrl,
    barcode = barcode,
    name = name,
    expireDate = expireDate,
    storeName = storeName,
    memo = memo,
    isMoneyCoupon = isMoneyCoupon,
    leftMoney = leftMoney,
    sentMemberName = sentMemberName
)
