package com.yapp.buddycon.domain.usecase

import com.yapp.buddycon.domain.repository.TokenRepository
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke(accessToken: String) =
        tokenRepository.saveToken(accessToken)
}