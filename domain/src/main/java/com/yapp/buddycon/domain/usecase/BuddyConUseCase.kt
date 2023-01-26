package com.yapp.buddycon.domain.usecase

import com.yapp.buddycon.domain.repository.BuddyConRepository
import javax.inject.Inject

// TODO Example 추후 삭제 예정
class BuddyConUseCase @Inject constructor(
    private val buddyConRepository: BuddyConRepository
){
    suspend operator fun invoke() =
        buddyConRepository.getString()
}