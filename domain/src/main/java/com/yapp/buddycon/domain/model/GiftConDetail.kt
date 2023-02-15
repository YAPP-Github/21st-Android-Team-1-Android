package com.yapp.buddycon.domain.model

data class GiftConDetail(
    var id  : Int =  -1,
    var imageUrl : String = "",
    var barcode: String = "",
    var name: String = "",
    var expireDate: String = "",
    var storeName: String = "",
    var memo : String = "",
    var isMoneyCoupon : Boolean = false,
    var leftMoney: Int =  -1
)