package com.yapp.buddycon.presentation.ui.giftcon

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.yapp.buddycon.domain.model.GiftConDetail
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseActivity
import com.yapp.buddycon.presentation.databinding.ActivityGiftConDetailBinding
import com.yapp.buddycon.presentation.utils.toPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*

@AndroidEntryPoint
class GiftConDetailActivity : BaseActivity<ActivityGiftConDetailBinding>(R.layout.activity_gift_con_detail) {

    private val giftConDetailViewModel: GiftConDetailViewModel by viewModels()
    private val giftId by lazy { intent?.getIntExtra(GIFTCON_ID, 0) ?: 0 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        giftConDetailViewModel.getGiftconDetailInfo(giftId)
        binding.appBar.ibnAppbarBack.setOnClickListener { finish() }
        observeGiftConDetail()
    }

    private fun observeGiftConDetail(){
        giftConDetailViewModel.couponDetailState
            .flowWithLifecycle(lifecycle,Lifecycle.State.STARTED)
            .onEach { showCouponInfo(it) }
            .launchIn(lifecycleScope)
    }

    private fun showCouponInfo(giftConDetail: GiftConDetail) = with(binding){
        Glide.with(ivCoupon.context)
            .load(giftConDetail.imageUrl)
            .into(ivCoupon)

        val (year, month, day) = giftConDetail.expireDate.split("-").map { it.toInt() }
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val expireDate = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month - 1)
            set(Calendar.DAY_OF_MONTH, day)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val diff = (today - expireDate) / (24 * 60 * 60 * 1000)
        if (diff in 0..14) {
            binding.btnExpireDate.isVisible = true
            binding.btnExpireDate.text = "D-${diff}"
            binding.btnExpireDate.setBackgroundResource(
                if (diff <= 7) R.drawable.bg_coupon_expire_date
                else R.drawable.bg_coupon_gray_expire_date
            )
        } else {
            binding.btnExpireDate.isVisible = false
        }

        tvCouponTitle.text = giftConDetail.name
        tvExpirationDateInfo.text = "${year}년 ${month}월 ${day}일"
        tvUsePlaceInfo.text = giftConDetail.storeName
        tvMemoInfo.setText(giftConDetail.memo)

        switchPriceCoupone.isChecked = giftConDetail.isMoneyCoupon
        clSparePrice.isVisible = giftConDetail.isMoneyCoupon
        vBorder4.isVisible = giftConDetail.isMoneyCoupon.not()

        if(giftConDetail.isMoneyCoupon.not()){
            val layoutParam = (btnCouponDelete.layoutParams as ConstraintLayout.LayoutParams)
            layoutParam.apply {
                topToBottom = vBorder4.id
                topMargin = 10.toPx(this@GiftConDetailActivity).toInt()
            }
        }
    }

    companion object{
        const val GIFTCON_ID = "GIFTCON_ID"

        fun newIntent(context: Context, giftId: Int) =
            Intent(context, GiftConDetailActivity::class.java).apply {
                putExtra(GIFTCON_ID, giftId)
            }
    }
}