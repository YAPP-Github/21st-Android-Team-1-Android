package com.yapp.buddycon.domain.repository

import com.yapp.buddycon.domain.model.Example
import kotlinx.coroutines.flow.Flow

// TODO Example 추후 삭제 예정
interface BuddyConRepository {
    suspend fun getString(): Flow<Example>
}