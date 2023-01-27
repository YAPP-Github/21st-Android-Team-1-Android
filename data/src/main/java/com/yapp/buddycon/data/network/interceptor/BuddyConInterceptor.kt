package com.yapp.buddycon.data.network.interceptor

import com.yapp.buddycon.data.network.api.RefreshTokenService
import com.yapp.buddycon.domain.model.UserInfo
import com.yapp.buddycon.domain.repository.TokenRepository
import com.yapp.buddycon.domain.repository.UserRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.create
import javax.inject.Inject


class BuddyConInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val userRepository: UserRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val tokenInfo = runBlocking { combine(
            tokenRepository.getToken(),
            tokenRepository.getRefreshToken(),
            tokenRepository.getTokenExpiration()
        ) { accessToken, refreshToken, accessTokenExpiresIn ->
            UserInfo(accessToken, refreshToken, accessTokenExpiresIn)
        }.first()}
//
//        var (accessToken, refreshToken, accessTokenExpiresIn) = tokenInfo
//        val currentTime = System.currentTimeMillis()
//
//        if(accessTokenExpiresIn < currentTime){
//            try{
//                val refreshTokenInfo = runBlocking { tokenRepository.requestRefreshToken(accessToken, refreshToken).first() }
//                accessToken = refreshTokenInfo.accessToken
//                refreshToken = refreshTokenInfo.refreshToken
//                accessTokenExpiresIn = refreshTokenInfo.accessTokenExpiresIn
//            }catch (_: Exception){
//
//            }
//        }

        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()
        return chain.proceed(chain.request())
    }
}