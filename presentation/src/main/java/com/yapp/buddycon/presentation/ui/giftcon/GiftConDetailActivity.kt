package com.yapp.buddycon.presentation.ui.giftcon

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseActivity
import com.yapp.buddycon.presentation.databinding.ActivityGiftConDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GiftConDetailActivity : BaseActivity<ActivityGiftConDetailBinding>(R.layout.activity_gift_con_detail) {

    private val giftConDetailViewModel: GiftConDetailViewModel by viewModels()
    private val giftId by lazy { intent?.getIntExtra(GIFTCON_ID, 0) ?: 0 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        giftConDetailViewModel.getGiftconDetailInfo(giftId)
    }

    companion object{
        const val GIFTCON_ID = "GIFTCON_ID"

        fun newIntent(context: Context, giftId: Int) =
            Intent(context, GiftConDetailActivity::class.java).apply {
                putExtra(GIFTCON_ID, giftId)
            }
    }
}