package com.yapp.buddycon.data.network.interceptor

import com.yapp.buddycon.domain.repository.TokenRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class RefreshTokenInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}