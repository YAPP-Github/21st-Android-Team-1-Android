package com.yapp.buddycon.presentation.ui.customcon

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseFragment
import com.yapp.buddycon.presentation.databinding.FragmentCustomconBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomConFragment : BaseFragment<FragmentCustomconBinding>(R.layout.fragment_customcon) {

    private val customConViewModel: CustomConViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = customConViewModel
    }
}