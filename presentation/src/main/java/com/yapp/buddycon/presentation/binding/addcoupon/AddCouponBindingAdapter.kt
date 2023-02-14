package com.yapp.buddycon.presentation.binding.addcoupon

import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.yapp.buddycon.domain.model.CouponInfo
import com.yapp.buddycon.domain.model.CouponInputInfo
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.ui.addCoupon.state.CouponInfoLoadState
import kotlinx.coroutines.flow.StateFlow

@BindingAdapter("android:expireDateDescriptionByState")
fun setSelected(view: TextView, couponInfoLoadState: CouponInfoLoadState<CouponInputInfo>?){
    couponInfoLoadState?.let {
        when(couponInfoLoadState) {
            is CouponInfoLoadState.NewGifticon -> {
                val builder = SpannableStringBuilder()
                val textWithStar = view.context.getString(R.string.add_coupon_expire_date_input)
                val titleHintSpannable = SpannableString(textWithStar)

                titleHintSpannable.setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(view.context, R.color.orange100)),
                    textWithStar.length - 1, textWithStar.length, 0
                )

                builder.append(titleHintSpannable)
                view.text = builder
            }
            else -> {
                view.text = view.context.getString(R.string.add_coupon_expire_date)
            }
        }
    }
}