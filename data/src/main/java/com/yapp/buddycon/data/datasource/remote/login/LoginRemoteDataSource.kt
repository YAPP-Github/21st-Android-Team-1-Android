package com.yapp.buddycon.data.datasource.remote.login

import com.yapp.buddycon.data.network.response.UserInfoResponse
import kotlinx.coroutines.flow.Flow

interface LoginRemoteDataSource {
    fun requestUserInfo(
        kakaoAccessToken: String,
        name: String,
        email: String?,
        gender: String?,
        ageRange: String?
    ): Flow<UserInfoResponse>
}