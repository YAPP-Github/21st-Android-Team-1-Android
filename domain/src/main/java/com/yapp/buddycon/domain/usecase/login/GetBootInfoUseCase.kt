package com.yapp.buddycon.domain.usecase.login

import com.yapp.buddycon.domain.repository.BootRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBootInfoUseCase @Inject constructor(
    private val bootRepository: BootRepository
) {
    operator fun invoke(): Flow<Boolean> = bootRepository.getBootInfo()
}