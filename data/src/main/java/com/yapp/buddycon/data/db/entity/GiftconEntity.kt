package com.yapp.buddycon.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "giftcon")
data class GiftconEntity(
    val expireDate: String,
    @PrimaryKey val id: Int,
    val imageUrl: String,
    val name: String
)