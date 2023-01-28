package com.yapp.buddycon.data.network.interceptor

import com.yapp.buddycon.domain.model.UserInfo
import com.yapp.buddycon.domain.repository.TokenRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject


class RefreshTokenInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        Timber.d("RefreshTokenInterceptor")
        val tokenInfo = runBlocking {
            combine(
                tokenRepository.getToken(),
                tokenRepository.getRefreshToken(),
                tokenRepository.getTokenExpiration()
            ) { accessToken, refreshToken, accessTokenExpiresIn ->
                UserInfo(accessToken, refreshToken, accessTokenExpiresIn)
            }.first()
        }

        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${tokenInfo.accessToken}")
            .build()
        return chain.proceed(newRequest)
    }
}