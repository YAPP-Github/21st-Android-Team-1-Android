package com.yapp.buddycon.presentation.binding.home

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.yapp.buddycon.presentation.R
import kotlinx.coroutines.flow.StateFlow

@BindingAdapter("setThemeAppearance")
fun setThemeAppearance(view: View, isSelected: Boolean){
    view.isSelected = isSelected
}

@BindingAdapter("setBoxVisible")
fun setBoxVisible(box : ConstraintLayout, mode : StateFlow<Boolean>){
    box.bringToFront()
    if (box.id == R.id.cl_close_theme_mode){
        if (mode.value) {
            box.visibility = View.VISIBLE
        } else {
            box.visibility = View.GONE
        }
    }else if (box.id == R.id.cl_open_theme_mode) {
        if (!mode.value) {
            box.visibility = View.VISIBLE
        } else {
            box.visibility = View.GONE
        }
    }
}
