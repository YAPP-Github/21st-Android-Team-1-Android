package com.yapp.buddycon.domain.usecase.login

import com.yapp.buddycon.domain.repository.BootRepository
import javax.inject.Inject

class SaveBootInfoUseCase @Inject constructor(
    private val bootRepository: BootRepository
) {
    suspend operator fun invoke() =
        bootRepository.saveBootInfo()
}