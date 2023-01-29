package com.yapp.buddycon.data.network.api

import com.yapp.buddycon.data.network.request.UserInfoRequest
import com.yapp.buddycon.data.network.response.UserInfoResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginService {
    @POST("api/v1/login")
    suspend fun requestUserInfo(
        @Body userInfoRequest: UserInfoRequest
    ): UserInfoResponse
}