package com.yapp.buddycon.presentation.binding.main

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.yapp.buddycon.presentation.R
import kotlinx.coroutines.flow.StateFlow

@BindingAdapter("android:setStyle")
fun setStyle(view: TextView, state: StateFlow<Int>) {
    when (view.tag) {
        "not_share" -> {
            view.setTextAppearance(if (state.value == 0) R.style.bold16 else R.style.regular14)
            view.setTextColor(
                if (state.value == 0) view.context.getColor(R.color.skb_blue)
                else view.context.getColor(R.color.gray60)
            )
        }
        "expiration_date" -> {
            view.setTextAppearance(if (state.value == 1) R.style.bold16 else R.style.regular14)
            view.setTextColor(
                if (state.value == 1) view.context.getColor(R.color.skb_blue)
                else view.context.getColor(R.color.gray60)
            )
        }
        "upload" -> {
            view.setTextAppearance(if (state.value == 2) R.style.bold16 else R.style.regular14)
            view.setTextColor(
                if (state.value == 2) view.context.getColor(R.color.skb_blue)
                else view.context.getColor(R.color.gray60)
            )
        }
        "name" -> {
            view.setTextAppearance(if (state.value == 3) R.style.bold16 else R.style.regular14)
            view.setTextColor(
                if (state.value == 3) view.context.getColor(R.color.skb_blue)
                else view.context.getColor(R.color.gray60)
            )
        }
        else -> Unit
    }
}