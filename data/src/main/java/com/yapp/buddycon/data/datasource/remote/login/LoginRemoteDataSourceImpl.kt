package com.yapp.buddycon.data.datasource.remote.login

import com.yapp.buddycon.data.network.api.LoginService
import com.yapp.buddycon.data.network.request.UserInfoRequest
import com.yapp.buddycon.data.network.response.UserInfoResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRemoteDataSourceImpl @Inject constructor(
    private val loginService: LoginService
) : LoginRemoteDataSource {

    override fun requestUserInfo(
        kakaoAccessToken: String,
        name: String,
        email: String?,
        gender: String?,
        ageRange: String?
    ): Flow<UserInfoResponse> = flow {
        emit(
            loginService.requestUserInfo(
                UserInfoRequest(
                    accessToken = kakaoAccessToken,
                    name = name,
                    email = email,
                    gender = gender,
                    ageRange = ageRange
                )
            )
        )
    }
}