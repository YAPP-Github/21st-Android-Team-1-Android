package com.yapp.buddycon.presentation.ui.giftcon

sealed class GiftConUserEvent {
    object CompleteUse : GiftConUserEvent()
    object CompleteUseFail : GiftConUserEvent()

    object MakeCustomCon : GiftConUserEvent()
    object MakeCustomConFail : GiftConUserEvent()

    object RollbackUsed : GiftConUserEvent()
    object RollbackUsedFail : GiftConUserEvent()

    data class Update(
        val name: String,
        val expireDate: String,
        val storeName: String,
        val memo: String,
        val isMoneyCoupon: Boolean,
        val leftMoney: Int
    ) : GiftConUserEvent()
    object UpdateFail : GiftConUserEvent()

    object Delete : GiftConUserEvent()
    object DeleteFail : GiftConUserEvent()

    object Error : GiftConUserEvent()
}