package com.yapp.buddycon.data.datasource.remote.token

import com.yapp.buddycon.data.network.api.RefreshTokenService
import com.yapp.buddycon.data.network.request.RefreshTokenRequest
import com.yapp.buddycon.data.network.response.UserInfoResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefreshTokenRemoteDataSourceImpl @Inject constructor(
    private val refreshTokenService: RefreshTokenService
) : RefreshTokenRemoteDataSource {

    override fun requestRefreshToken(
        accessToken: String,
        refreshToken: String
    ): Flow<UserInfoResponse> = flow {
        emit(
            refreshTokenService.requestRefreshToken(
                RefreshTokenRequest(
                    accessToken = accessToken,
                    refreshToken = refreshToken
                )
            )
        )
    }
}