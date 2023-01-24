package com.yapp.buddycon.domain.usecase

import com.yapp.buddycon.domain.repository.InitRepository
import javax.inject.Inject

class SaveInitInfoUseCase @Inject constructor(
    private val initRepository: InitRepository
) {
    suspend operator fun invoke() = initRepository.saveInitInfo()
}