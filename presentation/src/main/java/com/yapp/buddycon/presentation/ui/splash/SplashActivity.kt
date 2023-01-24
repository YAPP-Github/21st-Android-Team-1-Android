package com.yapp.buddycon.presentation.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.ViewTreeObserver
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseActivity
import com.yapp.buddycon.presentation.databinding.ActivitySplashBinding
import com.yapp.buddycon.presentation.ui.login.KakaoLoginActivity
import com.yapp.buddycon.presentation.ui.main.BuddyConActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private var isReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        initSplash()

        Handler(mainLooper).postDelayed({
            isReady = true
        }, 2000)
    }

    private fun initSplash(){
        binding.root.viewTreeObserver.addOnPreDrawListener(
            object: ViewTreeObserver.OnPreDrawListener{
                override fun onPreDraw(): Boolean {
                    return if(isReady){
                        binding.root.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    }else{
                        false
                    }
                }
            }
        )
    }
}