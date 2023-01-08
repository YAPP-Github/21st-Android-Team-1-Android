package com.yapp.buddycon.presentation.ui.makeCoupon

import android.os.Bundle
import android.view.View
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseActivity
import com.yapp.buddycon.presentation.databinding.ActivityMakeCouponBinding

class MakeCouponActivity : BaseActivity<ActivityMakeCouponBinding>(R.layout.activity_make_coupon) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.appbarMakeCoupon.tvAppbarRight.visibility = View.VISIBLE
        binding.appbarMakeCoupon.ibnAppbarBack.setOnClickListener { finish() }
    }
}