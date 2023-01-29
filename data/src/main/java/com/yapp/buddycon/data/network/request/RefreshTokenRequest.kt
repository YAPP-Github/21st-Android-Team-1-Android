package com.yapp.buddycon.data.network.request

data class RefreshTokenRequest(
    val accessToken: String,
    val refreshToken: String
)