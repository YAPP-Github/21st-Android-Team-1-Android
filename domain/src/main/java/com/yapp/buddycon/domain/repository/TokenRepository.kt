package com.yapp.buddycon.domain.repository

import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    fun getToken(): Flow<String>
    fun getTokenExpiration(): Flow<Long>
    suspend fun saveToken(accessToken: String, accessTokenExpiresIn: Long)
}