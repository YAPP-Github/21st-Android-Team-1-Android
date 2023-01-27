package com.yapp.buddycon.domain.usecase.token

import com.yapp.buddycon.domain.model.UserInfo
import com.yapp.buddycon.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    operator fun invoke(accessToken: String, refreshToken: String): Flow<UserInfo> =
        tokenRepository.requestRefreshToken(accessToken, refreshToken)
}