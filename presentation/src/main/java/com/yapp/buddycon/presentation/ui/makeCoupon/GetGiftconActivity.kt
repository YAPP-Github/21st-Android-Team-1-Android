package com.yapp.buddycon.presentation.ui.makeCoupon

import androidx.activity.viewModels
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseActivity
import com.yapp.buddycon.presentation.databinding.ActivityGetGiftconBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GetGiftConActivity : BaseActivity<ActivityGetGiftconBinding>(R.layout.activity_get_giftcon) {
    private val getCouponViewModel : GetGiftConViewModel by viewModels()


}