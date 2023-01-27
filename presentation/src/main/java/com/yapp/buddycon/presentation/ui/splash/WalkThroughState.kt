package com.yapp.buddycon.presentation.ui.splash

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.yapp.buddycon.presentation.R

data class WalkThroughState(
    val idx: Int,
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val subTitle: Int
)

fun Int.toWalkThroughImage() = when(this){
    0 -> R.drawable.img_first_walk_through
    1 -> R.drawable.img_second_walk_through
    2 -> R.drawable.img_third_walk_through
    else -> throw IllegalStateException()
}

fun Int.toWalkThroughTitle() = when (this) {
    0 -> R.string.walk_through_first_title
    1 -> R.string.walk_through_second_title
    2 -> R.string.walk_through_third_title
    else -> throw IllegalStateException()
}

fun Int.toWalkThroughSubTitle() = when(this){
    0 -> R.string.walk_through_first_subtitle
    1 -> R.string.walk_through_second_subtitle
    2 -> R.string.walk_through_third_subtitle
    else -> throw IllegalStateException()
}