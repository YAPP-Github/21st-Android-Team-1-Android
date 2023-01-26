package com.yapp.buddycon.data.network.interceptor

import com.yapp.buddycon.domain.repository.TokenRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

// TODO Retry 로직
class BuddyConInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking { tokenRepository.getToken().first() }
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()
        return chain.proceed(newRequest)
    }
}