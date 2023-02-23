package com.yapp.buddycon.presentation.ui.giftcon

sealed class GiftConUserEvent {
    object CompleteUse : GiftConUserEvent()
    object CompleteUseFail : GiftConUserEvent()

    object MakeCustomCon : GiftConUserEvent()
    object MakeCustomConFail : GiftConUserEvent()

    object RollbackUsed : GiftConUserEvent()
    object RollbackUsedFail : GiftConUserEvent()

    object Update : GiftConUserEvent()
    object UpdateFail : GiftConUserEvent()

    object Delete : GiftConUserEvent()
    object DeleteFail : GiftConUserEvent()

    object Error : GiftConUserEvent()
}