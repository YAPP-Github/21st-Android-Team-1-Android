package com.yapp.buddycon.domain.model

enum class TabMode {
    Usable, Used, Made
}

enum class SortMode(val value: String) {
    NoShared("createdAt,DESC"),
    ExpireDate("expireDate"),
    Name("name"),
    CreatedAt("createdAt")
}

enum class CouponType {
    GiftCon, Custom, Made
}
