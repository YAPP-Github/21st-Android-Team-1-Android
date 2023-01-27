package com.yapp.buddycon.domain.usecase.login

import com.yapp.buddycon.domain.repository.InitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInitInfoUseCase @Inject constructor(
    private val initRepository: InitRepository
) {
    operator fun invoke(): Flow<Boolean> = initRepository.getInitInfo()
}