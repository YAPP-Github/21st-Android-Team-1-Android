package com.yapp.buddycon.presentation.ui.addCoupon

import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseActivity
import com.yapp.buddycon.presentation.databinding.ActivityAddCouponBinding

class AddCouponActivity : BaseActivity<ActivityAddCouponBinding>(R.layout.activity_add_coupon) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        setAppbar()
        getImageUri()
    }

    private fun getImageUri() {
        val imgUri = intent.getParcelableExtra<Uri>("imageUri")
        Log.e("AppTest", "AddCouponActivity/ imgUri : $imgUri")

        binding.shivCoupon.setImageURI(imgUri) // 현재 테스트용으로 전달받은 이미지 uri로 이미지뷰에 보여주고 있음
    }

    private fun setAppbar() {
        binding.appbarAddCoupon.ibnAppbarBack.setOnClickListener { finish() }
    }
}