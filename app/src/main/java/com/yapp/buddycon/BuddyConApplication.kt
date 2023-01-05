package com.yapp.buddycon

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class BuddyConApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(applicationContext, BuildConfig.KAKAO_APP_KEY)
    }
}