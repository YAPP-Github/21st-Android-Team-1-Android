package com.yapp.buddycon.data.repository

import com.yapp.buddycon.data.datasource.remote.login.LoginRemoteDataSource
import com.yapp.buddycon.data.mapper.toModel
import com.yapp.buddycon.domain.model.UserInfo
import com.yapp.buddycon.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource
) : LoginRepository {
    override fun requestUserInfo(accessToken: String): Flow<Result<UserInfo>> = flow {
        loginRemoteDataSource.requestUserInfo(accessToken).collect { result ->
            result.onSuccess { response ->
                emit(Result.success(response.toModel()))
            }.onFailure { error ->
                emit(Result.failure(error))
            }
        }
    }
}