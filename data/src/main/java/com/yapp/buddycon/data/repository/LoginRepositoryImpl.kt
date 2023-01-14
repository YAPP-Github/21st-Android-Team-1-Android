package com.yapp.buddycon.data.repository

import com.yapp.buddycon.data.datasource.remote.login.LoginRemoteDataSource
import com.yapp.buddycon.data.mapper.toModel
import com.yapp.buddycon.data.network.response.UserInfoResponse
import com.yapp.buddycon.domain.model.UserInfo
import com.yapp.buddycon.domain.repository.LoginRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource
) : LoginRepository {
    override fun requestUserInfo(kakaoAccessToken: String): Flow<UserInfo> =
        loginRemoteDataSource.requestUserInfo(kakaoAccessToken)
            .map { it.toModel()}
}