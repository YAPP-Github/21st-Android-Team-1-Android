package com.yapp.buddycon.domain.repository

import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    fun getToken(): Flow<String>
    fun getTokenExpiration(): Flow<Long>
    fun getRefreshToken(): Flow<String>
    suspend fun saveToken(accessToken: String, accessTokenExpiresIn: Long, refreshToken: String)
}