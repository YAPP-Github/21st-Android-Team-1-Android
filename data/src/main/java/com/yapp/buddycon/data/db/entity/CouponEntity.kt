package com.yapp.buddycon.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coupon")
data class CouponEntity(
    @PrimaryKey val id: Int,
    val imageUrl: String,
    val name: String,
    val expireDate: String,
    val createdAt: String,
    val usable: Boolean = false,
    val shared: Boolean = false

)