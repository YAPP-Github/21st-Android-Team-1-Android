package com.yapp.buddycon.presentation.ui.splash

import androidx.lifecycle.ViewModel
import com.yapp.buddycon.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(

) : ViewModel() {
    private var idx = 0
    private val _walkThroughState = MutableStateFlow(WalkThroughState(
        idx = idx,
        image = idx.toWalkThroughImage(),
        title = idx.toWalkThroughTitle(),
        subTitle = idx.toWalkThroughSubTitle()
    ))

    val walkThroughState = _walkThroughState.asSharedFlow()

    fun nextWalkThrough(){
        idx++
        _walkThroughState.value = WalkThroughState(
            idx = idx,
            image = idx.toWalkThroughImage(),
            title = idx.toWalkThroughTitle(),
            subTitle = idx.toWalkThroughSubTitle()
        )
    }
}