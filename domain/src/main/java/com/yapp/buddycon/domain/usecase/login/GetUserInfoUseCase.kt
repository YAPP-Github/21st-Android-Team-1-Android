package com.yapp.buddycon.domain.usecase.login

import com.yapp.buddycon.domain.model.UserInfo
import com.yapp.buddycon.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        kakaoAccessToken: String,
        name: String,
        email: String? = null,
        gender: String? = null,
        ageRange: String? = null
    ): Flow<UserInfo> =
        userRepository.requestUserInfo(kakaoAccessToken, name, email, gender, ageRange)
}