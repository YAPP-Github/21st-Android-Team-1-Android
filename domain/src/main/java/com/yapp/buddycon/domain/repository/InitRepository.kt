package com.yapp.buddycon.domain.repository

import kotlinx.coroutines.flow.Flow

interface InitRepository {
    fun getInitInfo(): Flow<Boolean>
    suspend fun saveInitInfo()
}