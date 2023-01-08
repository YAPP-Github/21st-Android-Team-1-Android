package com.yapp.buddycon.data.datasource.remote.login

import com.yapp.buddycon.data.network.response.UserInfoResponse
import com.yapp.buddycon.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface LoginRemoteDataSource {
    fun requestUserInfo(accessToken: String): Flow<Result<UserInfoResponse>>
}