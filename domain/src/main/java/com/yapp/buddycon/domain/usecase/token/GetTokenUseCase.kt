package com.yapp.buddycon.domain.usecase.token

import com.yapp.buddycon.domain.model.UserInfo
import com.yapp.buddycon.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapMerge
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    operator fun invoke(): Flow<UserInfo> = combine(
        tokenRepository.getToken(),
        tokenRepository.getRefreshToken(),
        tokenRepository.getTokenExpiration(),
    ) { token, refreshToken, expiration ->
        UserInfo(token, refreshToken, expiration)
    }
}