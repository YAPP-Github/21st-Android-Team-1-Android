package com.yapp.buddycon.presentation.ui.addCoupon.state

import com.yapp.buddycon.domain.model.CouponDatailInfo

sealed class CouponInfoLoadState<out T> {
    object Init : CouponInfoLoadState<Nothing>()
    data class Loading(val isLoading: Boolean) : CouponInfoLoadState<Nothing>()
    data class LoadError(val error: Throwable?) : CouponInfoLoadState<Nothing>()
    data class NewGifticon(val barcodeNumber: String) : CouponInfoLoadState<Nothing>()
    data class ExistGifticon<T>(val data: CouponDatailInfo) : CouponInfoLoadState<T>()
    data class ExistMakeCon<T>(val data: CouponDatailInfo) : CouponInfoLoadState<T>()
}

sealed class WhetherInputPossibleState {
    object Possible: WhetherInputPossibleState()
    object Impossible: WhetherInputPossibleState()
}

data class ContentInputState(
    var isTitleNormal: Boolean = false,
    var isExipreDateNormal: Boolean = false,
    var isStoreNameNormal: Boolean = false,
    var isSentMemberNameNormal: Boolean = false,
    var isMemoNormal: Boolean = false
)