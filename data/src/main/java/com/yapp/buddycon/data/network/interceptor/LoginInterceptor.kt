package com.yapp.buddycon.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response


// TODO AccessToken Header 추가
class LoginInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }

    companion object{
        const val AUTHORIZATION = "Authorization"
    }
}