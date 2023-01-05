package com.yapp.buddycon.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseActivity
import com.yapp.buddycon.presentation.databinding.ActivitySplashBinding
import com.yapp.buddycon.presentation.ui.BuddyConActivity
import com.yapp.buddycon.presentation.ui.login.KakaoLoginActivity

// TODO : Android 12 대응
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, KakaoLoginActivity::class.java))
    }
}