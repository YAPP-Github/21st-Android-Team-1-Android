package com.yapp.buddycon.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

// TODO Token 및 Retry 로직
class BuddyConInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}