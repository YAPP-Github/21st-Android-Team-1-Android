package com.yapp.buddycon.presentation.ui.giftcon

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseFragment
import com.yapp.buddycon.presentation.databinding.FragmentGiftconBinding
import com.yapp.buddycon.presentation.ui.main.BuddyConActivity
import com.yapp.buddycon.presentation.ui.main.BuddyConViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class GiftConFragment : BaseFragment<FragmentGiftconBinding>(R.layout.fragment_giftcon) {

    private val buddyConViewModel: BuddyConViewModel by activityViewModels()
    private val giftConViewMoel: GiftConViewModel by viewModels()
    private lateinit var giftconAdapter: GiftconAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = giftConViewMoel
        binding.buddyConViewModel = buddyConViewModel

        initViews()
        observeFilterTab()
        observeGiftcon()
    }


    private fun initViews() {
        if (::giftconAdapter.isInitialized.not()) {
            giftconAdapter = GiftconAdapter()
        }
        binding.giftconRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        binding.giftconRecyclerView.adapter = giftconAdapter
    }

    private fun observeFilterTab() {
        buddyConViewModel.filterState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach {
                binding.tvFilter.text = when (it) {
                    0 -> "미공유순"
                    1 -> "유효기간순"
                    2 -> "등록순"
                    else -> "이름순"
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun observeGiftcon() {
        lifecycleScope.launch {
            giftConViewMoel.giftconPagingData.collectLatest {
                giftconAdapter.submitData(it)
            }
        }
    }

}