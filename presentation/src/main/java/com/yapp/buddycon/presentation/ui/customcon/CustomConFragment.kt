package com.yapp.buddycon.presentation.ui.customcon

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.yapp.buddycon.domain.model.CouponType
import com.yapp.buddycon.domain.model.SortMode
import com.yapp.buddycon.domain.model.TabMode
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseFragment
import com.yapp.buddycon.presentation.databinding.FragmentCustomconBinding
import com.yapp.buddycon.presentation.ui.main.BuddyConViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CustomConFragment : BaseFragment<FragmentCustomconBinding>(R.layout.fragment_customcon) {

    private val buddyConViewModel: BuddyConViewModel by activityViewModels()
    private val customConViewModel: CustomConViewModel by viewModels()
    private lateinit var customConAdapter: CustomConAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.customConViewModel = customConViewModel
        binding.buddyConViewModel = buddyConViewModel

        buddyConViewModel.changeTabMode(TabMode.Usable)
        buddyConViewModel.changeSortMode(SortMode.ExpireDate)
        buddyConViewModel.changeCouponType(CouponType.Custom)
        initViews()
        observeSortMode()
        observeCustomCoupon()
    }

    private fun initViews() {
        if (::customConAdapter.isInitialized.not()) {
            customConAdapter = CustomConAdapter()
        }
        binding.customconRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        binding.customconRecyclerView.adapter = customConAdapter
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

    private fun observeCustomCoupon() {
        lifecycleScope.launch {
            buddyConViewModel.couponPagingData.collectLatest {
                customConAdapter.submitData(it)
                binding.customconRecyclerView.scrollToPosition(0)
            }
        }

        lifecycleScope.launch {
            customConAdapter.loadStateFlow.collectLatest { loadState ->
                if(loadState.append.endOfPaginationReached){
                    val isListEmpty = customConAdapter.itemCount == 0
                    binding.ivEmpty.isVisible = isListEmpty
                    binding.tvEmpty.isVisible = isListEmpty
                    binding.tvEmpty.text = getString(
                        when(buddyConViewModel.tabModeState.value){
                            TabMode.Usable -> R.string.customcon_usable_empty_message
                            TabMode.Used -> R.string.customcon_used_empty_message
                            TabMode.Made -> R.string.made_coupon_empty_message
                        }
                    )
                    binding.customconRecyclerView.isVisible = isListEmpty.not()
                }
            }
        }
    }
}