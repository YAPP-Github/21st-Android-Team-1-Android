package com.yapp.buddycon.presentation.ui.makeCoupon

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MakeCouponViewModel : ViewModel() {

    enum class Theme {BASIC,CELEBRATE,FUN,IMAGE,GIFTCON}

    private val _chooseMode : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val chooseMode = _chooseMode.asStateFlow()

    private val _nowTheme : MutableStateFlow<Theme> = MutableStateFlow(Theme.BASIC)
    val nowTheme = _nowTheme.asStateFlow()

    fun changeMode(){
        _chooseMode.value = !_chooseMode.value
    }

    fun changeTheme(type : Theme){
        _nowTheme.value = type
    }

}
