package com.yapp.buddycon.data.network.request

data class UserInfoRequest(
    val accessToken: String,
    val name: String,
    val email: String? = null,
    val gender: String? = null,
    val ageRange: String? = null
)
