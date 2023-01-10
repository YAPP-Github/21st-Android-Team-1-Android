package com.yapp.buddycon.domain.usecase

import com.yapp.buddycon.domain.model.UserInfo
import com.yapp.buddycon.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    operator fun invoke(kakaoAccessToken: String): Flow<UserInfo> =
        loginRepository.requestUserInfo(kakaoAccessToken)
}