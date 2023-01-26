package com.yapp.buddycon.data.repository

import com.yapp.buddycon.data.network.api.BuddyConService
import com.yapp.buddycon.domain.model.Example
import com.yapp.buddycon.domain.repository.BuddyConRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BuddyConRepositoryImpl @Inject constructor(
    private val service: BuddyConService
): BuddyConRepository {

    override suspend fun getString(): Flow<Example> = flow {
        emit(service.getString())
    }.map {
        Example(
            message = it.message
        )
    }
}