package com.yapp.buddycon.data.network.api

import com.yapp.buddycon.data.network.response.UserInfoResponse
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginService {
    @POST("api/vi/login/{accessToken}")
    suspend fun requestUserInfo(
        @Path("accessToken") accessToken: String
    ): UserInfoResponse
}