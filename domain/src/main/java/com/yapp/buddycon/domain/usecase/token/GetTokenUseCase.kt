package com.yapp.buddycon.domain.usecase.token

import com.yapp.buddycon.domain.model.UserInfo
import com.yapp.buddycon.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    operator fun invoke(): Flow<UserInfo> = tokenRepository.getToken()
        .zip(tokenRepository.getRefreshToken()){ accessToken, refreshToken -> UserInfo(accessToken, refreshToken, 0L)}
        .zip(tokenRepository.getTokenExpiration()) { userInfo, accessTokenExpiresIn -> userInfo.copy(accessTokenExpiresIn = accessTokenExpiresIn)}
}