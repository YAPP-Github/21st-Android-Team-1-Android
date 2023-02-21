package com.yapp.buddycon.presentation.ui.giftcon

sealed class GiftConUserEvent {
    object CompleteUse: GiftConUserEvent()
    object MakeCustomCon: GiftConUserEvent()
    object RollbackUsed: GiftConUserEvent()
    object Delete: GiftConUserEvent()
}