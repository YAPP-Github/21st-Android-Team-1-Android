package com.yapp.buddycon.presentation.ui.customcon

sealed class CustomCouponUserEvent {
    object CompleteUse : CustomCouponUserEvent()
    object CompleteUseFail : CustomCouponUserEvent()

    object RollbackUsed : CustomCouponUserEvent()
    object RollbackUsedFail : CustomCouponUserEvent()

    data class Update(
        val expireDate: String,
        val memo: String
    ) : CustomCouponUserEvent()
    object UpdateFail : CustomCouponUserEvent()

    object Delete : CustomCouponUserEvent()
    object DeleteFail : CustomCouponUserEvent()

    object Error : CustomCouponUserEvent()
}