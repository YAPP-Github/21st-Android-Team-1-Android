package com.yapp.buddycon.domain.repository

import com.yapp.buddycon.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    fun getToken(): Flow<String>
    fun getTokenExpiration(): Flow<Long>
    fun getRefreshToken(): Flow<String>
    fun requestRefreshToken(accessToken: String, refreshToken: String): Flow<UserInfo>
    suspend fun saveToken(accessToken: String, accessTokenExpiresIn: Long, refreshToken: String)
}