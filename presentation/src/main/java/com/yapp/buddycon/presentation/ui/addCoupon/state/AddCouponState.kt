package com.yapp.buddycon.presentation.ui.addCoupon.state

import com.yapp.buddycon.domain.model.CouponInfo
import com.yapp.buddycon.domain.model.CouponInputInfo

sealed class CouponInfoLoadState<out T> {
    object Init : CouponInfoLoadState<Nothing>()
    //data class Loading(val isLoading: Boolean) : CouponInfoLoadState<Nothing>()
    object ShowLoading: CouponInfoLoadState<Nothing>()
    object HideLoading: CouponInfoLoadState<Nothing>()
    data class LoadError(val error: Throwable?) : CouponInfoLoadState<Nothing>()
    data class NewGifticon(val barcodeNumber: String) : CouponInfoLoadState<Nothing>()
    data class ExistGifticon<T>(val data: CouponInputInfo) : CouponInfoLoadState<T>()
    data class ExistMakeCon<T>(val data: CouponInputInfo) : CouponInfoLoadState<T>()
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