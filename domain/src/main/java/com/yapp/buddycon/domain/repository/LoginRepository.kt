package com.yapp.buddycon.domain.repository

import com.yapp.buddycon.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun requestUserInfo(
        kakaoAccessToken: String,
        name: String,
        email: String?,
        gender: String?,
        ageRange: String?
    ): Flow<UserInfo>
}