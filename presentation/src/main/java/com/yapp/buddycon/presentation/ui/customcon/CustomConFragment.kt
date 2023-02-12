package com.yapp.buddycon.presentation.ui.customcon

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.yapp.buddycon.domain.model.SortMode
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseFragment
import com.yapp.buddycon.presentation.databinding.FragmentCustomconBinding
import com.yapp.buddycon.presentation.ui.main.BuddyConViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CustomConFragment : BaseFragment<FragmentCustomconBinding>(R.layout.fragment_customcon) {

    private val buddyConViewModel: BuddyConViewModel by activityViewModels()
    private val customConViewModel: CustomConViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = customConViewModel
        binding.buddyConViewModel = buddyConViewModel
    }

    override fun onResume() {
        super.onResume()

        buddyConViewModel.sortModeState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach {
                binding.tvSortMode.text = when (it) {
                    SortMode.NoShared -> "미공유순"
                    SortMode.ExpireDate -> "유효기간순"
                    SortMode.CreatedAt -> "등록순"
                    SortMode.Name -> "이름순"
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}