package com.yapp.buddycon.presentation.ui.makeCoupon

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseActivity
import com.yapp.buddycon.presentation.databinding.ActivityMakeCouponBinding
import kotlinx.coroutines.launch
import com.yapp.buddycon.presentation.ui.makeCoupon.MakeCouponViewModel.Theme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MakeCouponActivity : BaseActivity<ActivityMakeCouponBinding>(R.layout.activity_make_coupon) {
    private val couponViewModel: MakeCouponViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clOpenThemeMode.bringToFront()
        binding.clCloseThemeMode.bringToFront()

        binding.couponViewModel = couponViewModel
        binding.appbarMakeCoupon.tvAppbarRight.visibility = View.VISIBLE
        binding.appbarMakeCoupon.ibnAppbarBack.setOnClickListener { finish() }

        modeCollect()
        themeCollect()
    }

    private fun themeCollect() {
        couponViewModel.nowTheme.flowWithLifecycle(lifecycle,Lifecycle.State.RESUMED).onEach {
            var mainResource = 0
            var foreground = 0
            when(it){
                Theme.BASIC -> {
                    mainResource = R.drawable.img_theme1
                    foreground = R.drawable.bg_theme1
                }
                Theme.CELEBRATE -> {
                    mainResource = R.drawable.img_theme2
                    foreground = R.drawable.bg_theme2
                }
                Theme.FUN -> {
                    mainResource = R.drawable.img_theme3
                    foreground = R.drawable.bg_theme3
                }
            }
            binding.ivThumbnail.setImageResource(mainResource)
            binding.ivMain.setImageResource(mainResource)
            binding.cl.foreground = getDrawable(foreground)
        }.launchIn(lifecycleScope)
    }

    fun modeCollect() {
        couponViewModel.chooseMode.flowWithLifecycle(lifecycle,Lifecycle.State.RESUMED).onEach {
            if (it) {
                binding.clCloseThemeMode.visibility = View.VISIBLE
                binding.clOpenThemeMode.visibility = View.GONE
            } else {
                binding.clCloseThemeMode.visibility = View.GONE
                binding.clOpenThemeMode.visibility = View.VISIBLE
            }
        }.launchIn(lifecycleScope)
    }
}