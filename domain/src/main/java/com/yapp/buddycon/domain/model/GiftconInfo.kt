package com.yapp.buddycon.domain.model

data class GiftconInfo(
    val expireDate: String,
    val id: Int,
    val imageUrl: String,
    val name: String,
    val usable: Boolean
)