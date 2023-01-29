package com.yapp.buddycon.domain.repository

import kotlinx.coroutines.flow.Flow

interface BootRepository {
    fun getBootInfo(): Flow<Boolean>
    suspend fun saveBootInfo()
}