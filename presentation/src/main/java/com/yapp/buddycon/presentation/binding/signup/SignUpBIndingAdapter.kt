package com.yapp.buddycon.presentation.binding.signup

import android.view.View
import androidx.databinding.BindingAdapter
import kotlinx.coroutines.flow.StateFlow

@BindingAdapter("android:isSelected")
fun setSelected(view: View, isSelected: Boolean){
    view.isSelected = isSelected
}