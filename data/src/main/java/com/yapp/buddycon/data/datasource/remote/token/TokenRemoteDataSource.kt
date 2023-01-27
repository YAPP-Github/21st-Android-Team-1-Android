package com.yapp.buddycon.data.datasource.remote.token

import com.yapp.buddycon.data.network.response.UserInfoResponse
import kotlinx.coroutines.flow.Flow

interface TokenRemoteDataSource {
    fun requestRefreshToken(
        accessToken: String,
        refreshToken: String
    ): Flow<UserInfoResponse>
}