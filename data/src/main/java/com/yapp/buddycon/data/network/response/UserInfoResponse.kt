package com.yapp.buddycon.data.network.response


data class UserInfoResponse(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiresIn: Long,
    val grantType: String
)