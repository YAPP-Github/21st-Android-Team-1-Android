package com.yapp.buddycon.domain.model

data class GiftConDetail(
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
)

fun checkUpdateCoupon(coupon1: GiftConDetail, coupon2: GiftConDetail): Boolean {
    return coupon1.name == coupon2.name &&
            coupon1.expireDate == coupon2.expireDate &&
            coupon1.storeName == coupon2.storeName &&
            coupon1.memo == coupon2.memo &&
            coupon1.isMoneyCoupon == coupon2.isMoneyCoupon &&
            coupon1.leftMoney == coupon2.leftMoney
}