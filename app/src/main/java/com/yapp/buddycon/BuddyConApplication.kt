package com.yapp.buddycon

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import timber.log.Timber

class BuddyConApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        KakaoSdk.init(applicationContext, BuildConfig.KAKAO_APP_KEY)
    }
}