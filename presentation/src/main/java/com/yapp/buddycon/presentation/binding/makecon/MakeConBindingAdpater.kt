package com.yapp.buddycon.presentation.binding.makecon

import android.provider.Settings.Global.getString
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.yapp.buddycon.domain.model.CouponItem
import com.yapp.buddycon.presentation.R
import kotlinx.coroutines.flow.StateFlow
import com.yapp.buddycon.presentation.ui.makeCoupon.MakeCouponViewModel.Theme

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

@BindingAdapter("imageThemeMode")
fun imageThemeMode(imageView : ImageView , theme : StateFlow<Theme>){
    when (theme.value) {
        Theme.BASIC -> {
            imageView.setImageResource(R.drawable.img_theme1)
        }
        Theme.CELEBRATE -> {
            imageView.setImageResource(R.drawable.img_theme2)
        }
        Theme.FUN -> {
            imageView.setImageResource(R.drawable.img_theme3)
        }
        else -> {}
    }
}

@BindingAdapter("bgThemeMode")
fun bgThemeMode(layout : ConstraintLayout , theme : StateFlow<Theme>){
    when (theme.value) {
        Theme.BASIC -> {
            layout.foreground = getDrawable(layout.context,R.drawable.bg_theme1)
        }
        Theme.CELEBRATE -> {
            layout.foreground = getDrawable(layout.context,R.drawable.bg_theme2)
        }
        Theme.FUN -> {
            layout.foreground = getDrawable(layout.context,R.drawable.bg_theme3)
        }
        else -> {}
    }
}

@BindingAdapter("getGiftConBtn")
fun getGiftConBtn(button : Button, theme : StateFlow<Theme>){
    if (theme.value == Theme.GIFTCON || theme.value == Theme.IMAGE ){
        when(button.id){
            R.id.btn_get_giftcon -> {
                button.visibility = View.GONE
            }
            R.id.btn_get_img -> {
                button.setText(R.string.makecon_change_img)
            }
        }

    }


}

