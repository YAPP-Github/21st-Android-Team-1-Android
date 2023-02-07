package com.yapp.buddycon.presentation.ui.giftcon

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yapp.buddycon.domain.model.GiftconInfo
import com.yapp.buddycon.presentation.databinding.ItemCouponBinding

class GiftconAdapter : PagingDataAdapter<GiftconInfo, GiftconAdapter.GiftconViewHoler>(GIFTCON_DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: GiftconViewHoler, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiftconViewHoler {
        return GiftconViewHoler(
            ItemCouponBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    class GiftconViewHoler(
        private val binding: ItemCouponBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(info: GiftconInfo) {
            binding.itemCouponTvTitle.text = info.name
            binding.itemTvExpirationPeriod.text = "~${info.expireDate.replace("-",".")}"
            Glide.with(binding.itemCouponImg.context)
                .load(info.imageUrl)
                .into(binding.itemCouponImg)
        }
    }

    companion object {
        private val GIFTCON_DIFF_CALLBACK = object : DiffUtil.ItemCallback<GiftconInfo>() {
            override fun areItemsTheSame(oldItem: GiftconInfo, newItem: GiftconInfo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GiftconInfo, newItem: GiftconInfo): Boolean {
                return oldItem == newItem
            }
        }
    }
}