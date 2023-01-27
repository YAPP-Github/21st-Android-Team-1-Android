package com.yapp.buddycon.presentation.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseActivity
import com.yapp.buddycon.presentation.databinding.ActivitySplashBinding
import com.yapp.buddycon.presentation.ui.login.KakaoLoginActivity
import com.yapp.buddycon.presentation.ui.main.BuddyConActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private var isReady = false
    private var isFirst = false
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding.viewModel = splashViewModel
        initSplash()
        bindViews()

        splashViewModel.walkThroughState
            .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
            .onEach { invalidateView(it) }
            .launchIn(lifecycleScope)
    }

    private fun initSplash() {
        binding.root.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (isReady) {
                        binding.root.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        false
                    }
                }
            }
        )

        Handler(mainLooper).postDelayed({
            finishSplash()
            isReady = true
        }, 2000)
    }

    private fun bindViews() {
        binding.appbarSplash.tvSkip.setOnClickListener {
            startActivity(KakaoLoginActivity.newIntent(this, true))
            finish()
        }

        binding.btnStart.setOnClickListener {
            startActivity(KakaoLoginActivity.newIntent(this, true))
            finish()
        }
    }

    private fun invalidateView(state: WalkThroughState) {
        Glide.with(binding.ivWalkThrough.context)
            .load(state.image)
            .into(binding.ivWalkThrough)

        binding.tvWalkThroughTitle.text = getString(state.title)
        binding.tvWalkThroughSubtitle.text = getString(state.subTitle)
        binding.vFirstIndicator.isSelected = state.idx == 0
        binding.vSecondIndicator.isSelected = state.idx == 1
        binding.vThirdIndicator.isSelected = state.idx == 2

        if (state.idx == 2) {
            binding.btnNext.visibility = View.INVISIBLE
            binding.btnStart.isVisible = true
        }
    }

    private fun finishSplash() {
        when (splashViewModel.splashResultState.value) {
            SplashResultState.WalkThrough -> {
                binding.splashGrop.isVisible = false
                binding.walkThroughGroup.isVisible = true
            }
            SplashResultState.KaKaoLogin -> {
                startActivity(KakaoLoginActivity.newIntent(this))
                finish()
            }
            SplashResultState.BuddyCon -> {
                startActivity(BuddyConActivity.newIntent(this))
                finish()
            }
            else -> Unit
        }
    }
}