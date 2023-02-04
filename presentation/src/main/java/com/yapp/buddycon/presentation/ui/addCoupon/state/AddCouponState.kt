package com.yapp.buddycon.presentation.ui.addCoupon.state

sealed class CouponInfoLoadState<out T> {
    object Init : CouponInfoLoadState<Nothing>()
    data class Loading(val isLoading: Boolean) : CouponInfoLoadState<Nothing>()
    data class LoadError(val error: Throwable?) : CouponInfoLoadState<Nothing>()
    data class NewGifticon<T>(val data: T) : CouponInfoLoadState<T>()
    data class ExistGifticon<T>(val data: String) : CouponInfoLoadState<T>()
    data class ExistMakeCon<T>(val data: String) : CouponInfoLoadState<T>()
}