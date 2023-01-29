package com.yapp.buddycon.domain.model


data class UserInfo(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiresIn: Long
)