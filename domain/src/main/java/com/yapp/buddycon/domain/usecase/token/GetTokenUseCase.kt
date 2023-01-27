package com.yapp.buddycon.domain.usecase.token

import com.yapp.buddycon.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    operator fun invoke(): Flow<Pair<String, Long>> =
        tokenRepository.getToken()
            .combine(tokenRepository.getTokenExpiration()) { token, expiration ->
                token to expiration
            }
}