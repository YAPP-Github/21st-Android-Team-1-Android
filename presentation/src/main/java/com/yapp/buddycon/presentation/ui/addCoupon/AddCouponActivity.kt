package com.yapp.buddycon.presentation.ui.addCoupon

import android.os.Bundle
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseActivity
import com.yapp.buddycon.presentation.databinding.ActivityAddCouponBinding
import com.yapp.buddycon.presentation.databinding.ActivityBuddyConBinding

class AddCouponActivity : BaseActivity<ActivityAddCouponBinding>(R.layout.activity_add_coupon) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.appbarAddCoupon.ibnAppbarBack.setOnClickListener { finish() }
    }
}