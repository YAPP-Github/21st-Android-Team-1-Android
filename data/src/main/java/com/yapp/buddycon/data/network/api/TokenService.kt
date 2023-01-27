package com.yapp.buddycon.data.network.api

import com.yapp.buddycon.data.network.request.RefreshTokenRequest
import com.yapp.buddycon.data.network.response.UserInfoResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenService {
    @POST("api/v1/reissue")
    suspend fun requestRefreshToken(
        @Body refreshTokenRequest: RefreshTokenRequest
    ): UserInfoResponse
}