package com.yapp.buddycon.presentation.ui.giftcon

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.yapp.buddycon.domain.repository.SortMode
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseFragment
import com.yapp.buddycon.presentation.databinding.FragmentGiftconBinding
import com.yapp.buddycon.presentation.ui.main.BuddyConViewModel
import com.yapp.buddycon.presentation.ui.main.TabMode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GiftConFragment : BaseFragment<FragmentGiftconBinding>(R.layout.fragment_giftcon) {

    private val buddyConViewModel: BuddyConViewModel by activityViewModels()
    private val giftConViewMoel: GiftConViewModel by viewModels()
    private lateinit var giftconAdapter: GiftconAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.giftConViewModel = giftConViewMoel
        binding.buddyConViewModel = buddyConViewModel

        buddyConViewModel.changeTabMode(TabMode.Usable)
        buddyConViewModel.changeSortMode(SortMode.ExpireDate)
        initViews()
        observeSortMode()
        observeGiftcon()
    }


    private fun initViews() {
        if (::giftconAdapter.isInitialized.not()) {
            giftconAdapter = GiftconAdapter()
        }
        binding.giftconRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        binding.giftconRecyclerView.adapter = giftconAdapter
    }

    private fun observeSortMode() {
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

    private fun observeGiftcon() {
        lifecycleScope.launch {
            buddyConViewModel.couponPagingData.collectLatest {
                giftconAdapter.submitData(it)
                binding.giftconRecyclerView.scrollToPosition(0)
            }
        }
    }

}