package com.yapp.buddycon.domain.repository

import com.yapp.buddycon.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun requestUserInfo(
        kakaoAccessToken: String,
        name: String,
        email: String?,
        gender: String?,
        ageRange: String?
    ): Flow<UserInfo>

    fun requestRefreshToken(accessToken: String, refreshToken: String): Flow<UserInfo>
}