package com.yapp.buddycon.presentation.binding.main

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.yapp.buddycon.domain.model.SortMode
import com.yapp.buddycon.presentation.R
import kotlinx.coroutines.flow.StateFlow

@BindingAdapter("android:setStyle")
fun setStyle(view: TextView, state: StateFlow<SortMode>) {
    view.setTextAppearance(if (view.tag == state.value.value) R.style.bold16 else R.style.regular14)
    view.setTextColor(
        if (view.tag == state.value.value) view.context.getColor(R.color.skb_blue)
        else view.context.getColor(R.color.gray60)
    )
}