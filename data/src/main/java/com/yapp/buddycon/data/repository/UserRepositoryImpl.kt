package com.yapp.buddycon.data.repository

import com.yapp.buddycon.data.datasource.remote.login.LoginRemoteDataSource
import com.yapp.buddycon.domain.model.UserInfo
import com.yapp.buddycon.domain.repository.UserRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource
) : UserRepository {

    override fun requestUserInfo(
        kakaoAccessToken: String,
        name: String,
        email: String?,
        gender: String?,
        ageRange: String?
    ): Flow<UserInfo> =
        loginRemoteDataSource.requestUserInfo(kakaoAccessToken, name, email, gender, ageRange)
            .map { it.toModel() }

    override fun requestRefreshToken(accessToken: String, refreshToken: String): Flow<UserInfo> =
        loginRemoteDataSource.requestRefreshToken(accessToken, refreshToken)
            .map { it.toModel() }
}