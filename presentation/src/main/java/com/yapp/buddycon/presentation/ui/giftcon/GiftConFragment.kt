package com.yapp.buddycon.presentation.ui.giftcon

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.yapp.buddycon.domain.model.CouponType
import com.yapp.buddycon.domain.model.SortMode
import com.yapp.buddycon.domain.model.TabMode
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseFragment
import com.yapp.buddycon.presentation.databinding.FragmentGiftconBinding
import com.yapp.buddycon.presentation.ui.main.BuddyConViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GiftConFragment : BaseFragment<FragmentGiftconBinding>(R.layout.fragment_giftcon) {

    private val buddyConViewModel: BuddyConViewModel by activityViewModels()
    private lateinit var giftconAdapter: GiftConAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buddyConViewModel = buddyConViewModel

        buddyConViewModel.changeTabMode(TabMode.Usable)
        buddyConViewModel.changeSortMode(SortMode.ExpireDate)
        buddyConViewModel.changeCouponType(CouponType.GiftCon)
        initViews()
        observeSortMode()
        observeGiftcon()
    }


    private fun initViews() {
        if (::giftconAdapter.isInitialized.not()) {
            giftconAdapter = GiftConAdapter { item ->
                activity?.let {
                    startActivity(
                        GiftConDetailActivity.newIntent(it, item.id, item.usable)
                    )
                }
            }
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
        viewLifecycleOwner.lifecycleScope.launch {
            buddyConViewModel.couponPagingData.collectLatest {
                giftconAdapter.submitData(it)
                binding.giftconRecyclerView.scrollToPosition(0)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            giftconAdapter.loadStateFlow.collectLatest { loadState ->
                if (loadState.append.endOfPaginationReached) {
                    val isListEmpty = giftconAdapter.itemCount == 0
                    binding.ivEmpty.isVisible = isListEmpty
                    binding.tvEmpty.isVisible = isListEmpty
                    binding.tvEmpty.text = getString(
                        if (buddyConViewModel.tabModeState.value == TabMode.Usable) R.string.giftcon_usable_empty_message
                        else R.string.giftcon_used_empty_message
                    )
                    binding
                    binding.giftconRecyclerView.isVisible = isListEmpty.not()
                }
            }
        }
    }

}