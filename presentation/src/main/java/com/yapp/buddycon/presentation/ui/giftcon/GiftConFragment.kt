package com.yapp.buddycon.presentation.ui.giftcon

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseFragment
import com.yapp.buddycon.presentation.databinding.FragmentGiftconBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GiftConFragment : BaseFragment<FragmentGiftconBinding>(R.layout.fragment_giftcon) {

    private val giftConViewMoel: GiftConViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = giftConViewMoel
    }
}