package com.yapp.buddycon.domain.repository

import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    fun getToken(): Flow<String>
    suspend fun saveToken(accessToken: String)
}