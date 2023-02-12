package com.yapp.buddycon.presentation.binding.makecon

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.yapp.buddycon.domain.model.CouponItem
import com.yapp.buddycon.presentation.R
import kotlinx.coroutines.flow.StateFlow

@BindingAdapter("setSelectedButton")
fun setSelectedButton(text : TextView, item : StateFlow<CouponItem>){
    if (item.value.id == -1){
        text.setTextColor(text.resources.getColor(R.color.gray60))
        text.background = text.resources.getDrawable(R.color.gray40)
        text.isClickable = false
    }else{
        text.setTextColor(text.resources.getColor(R.color.white))
        text.background = text.resources.getDrawable(R.color.skb_blue)
        text.isClickable = true
    }
}