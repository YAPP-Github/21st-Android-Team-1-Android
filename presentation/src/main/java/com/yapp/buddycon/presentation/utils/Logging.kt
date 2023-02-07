package com.yapp.buddycon.presentation.utils

import timber.log.Timber

object Logging {
    private const val TAG = "AppTest"

    fun error(msg: String) {
        Timber.tag(TAG).e(msg)
    }
}