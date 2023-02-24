package com.yapp.buddycon.presentation.utils

import android.content.Context
import java.util.Calendar

fun Int.toPx(context: Context) =
    this * context.resources.displayMetrics.density

fun Calendar.getDday(year: Int, month: Int, day: Int): Long {
    val today = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis

    val expireDate = this.apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month - 1)
        set(Calendar.DAY_OF_MONTH, day)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis

    return (expireDate - today) / (24 * 60 * 60 * 1000)
}
