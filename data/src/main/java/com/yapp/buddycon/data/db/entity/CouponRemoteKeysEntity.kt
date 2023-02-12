package com.yapp.buddycon.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coupon_remote_keys")
data class CouponRemoteKeysEntity(
    @PrimaryKey val id: Int,
    val prevKey: Int?,
    val nextKey: Int?
)