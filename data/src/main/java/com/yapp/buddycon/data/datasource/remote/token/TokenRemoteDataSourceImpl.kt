package com.yapp.buddycon.data.datasource.remote.token

import com.yapp.buddycon.data.network.api.TokenService
import com.yapp.buddycon.data.network.request.RefreshTokenRequest
import com.yapp.buddycon.data.network.response.UserInfoResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TokenRemoteDataSourceImpl @Inject constructor(
    private val tokenService: TokenService
): TokenRemoteDataSource {

    override fun requestRefreshToken(
        accessToken: String,
        refreshToken: String
    ): Flow<UserInfoResponse> = flow {
        emit(
            tokenService.requestRefreshToken(
                RefreshTokenRequest(
                    accessToken = accessToken,
                    refreshToken = refreshToken
                )
            )
        )
    }
}