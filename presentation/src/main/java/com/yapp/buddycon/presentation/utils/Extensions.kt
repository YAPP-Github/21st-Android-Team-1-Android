package com.yapp.buddycon.presentation.utils

import android.content.Context

fun Int.toPx(context: Context) =
    this * context.resources.displayMetrics.density
