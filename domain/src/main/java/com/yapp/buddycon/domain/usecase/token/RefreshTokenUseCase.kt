package com.yapp.buddycon.domain.usecase.token

import com.yapp.buddycon.domain.model.UserInfo
import com.yapp.buddycon.domain.repository.TokenRepository
import com.yapp.buddycon.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(accessToken: String, refreshToken: String): Flow<UserInfo> =
        userRepository.requestRefreshToken(accessToken, refreshToken)
}