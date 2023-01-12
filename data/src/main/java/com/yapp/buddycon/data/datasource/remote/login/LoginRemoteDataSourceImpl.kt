package com.yapp.buddycon.data.datasource.remote.login

import com.yapp.buddycon.data.mapper.toModel
import com.yapp.buddycon.data.network.api.LoginService
import com.yapp.buddycon.data.network.response.UserInfoResponse
import com.yapp.buddycon.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRemoteDataSourceImpl @Inject constructor(
    private val loginService: LoginService
) : LoginRemoteDataSource {
    override fun requestUserInfo(kakaoAccessToken: String): Flow<UserInfoResponse> = flow {
        emit(loginService.requestUserInfo(kakaoAccessToken))
    }
}