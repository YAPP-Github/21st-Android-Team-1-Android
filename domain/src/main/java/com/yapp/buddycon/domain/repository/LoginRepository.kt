package com.yapp.buddycon.domain.repository

import com.yapp.buddycon.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun requestUserInfo(kakaoAccessToken: String): Flow<Result<UserInfo>>
}