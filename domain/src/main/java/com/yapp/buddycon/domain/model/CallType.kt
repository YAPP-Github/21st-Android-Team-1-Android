package com.yapp.buddycon.domain.model

enum class TabMode {
    Usable, Used, Custom
}

enum class SortMode(val value: String) {
    NoShared("noshared"),
    ExpireDate("expireDate"),
    Name("name"),
    CreatedAt("createdAt")
}

enum class CouponType {
    GiftCon, Custom, Made
}
