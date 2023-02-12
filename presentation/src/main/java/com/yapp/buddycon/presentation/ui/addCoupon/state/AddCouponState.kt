package com.yapp.buddycon.presentation.ui.addCoupon.state


import com.yapp.buddycon.domain.model.CouponInfo

sealed class CouponInfoLoadState<out T> {
    object Init : CouponInfoLoadState<Nothing>()
    data class Loading(val isLoading: Boolean) : CouponInfoLoadState<Nothing>()
    data class LoadError(val error: Throwable?) : CouponInfoLoadState<Nothing>()
    data class NewGifticon(val barcodeNumber: String) : CouponInfoLoadState<Nothing>()
    data class ExistGifticon<T>(val data: CouponInfo) : CouponInfoLoadState<T>()
    data class ExistMakeCon<T>(val data: CouponInfo) : CouponInfoLoadState<T>()
}

sealed class WhetherInputPossibleState {
    object Possible : WhetherInputPossibleState()
    object Impossible : WhetherInputPossibleState()
}

sealed class ContentInputState {
    object EmptyTitle : ContentInputState()
    object OutOfRangeTitle : ContentInputState()
    object EmptyExpireDate : ContentInputState()
    object OutOfRangeStoreName : ContentInputState()
    object OutOfRangeMemo : ContentInputState()
}