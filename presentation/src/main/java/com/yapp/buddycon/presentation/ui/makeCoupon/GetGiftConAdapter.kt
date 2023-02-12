package com.yapp.buddycon.presentation.ui.giftcon

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yapp.buddycon.domain.model.CouponItem
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.databinding.ItemCouponBinding

class GetGiftConAdapter : PagingDataAdapter<CouponItem, GetGiftConAdapter.GetGiftconViewHoler>(GIFTCON_DIFF_CALLBACK) {
    override fun onBindViewHolder(holder: GetGiftconViewHoler, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetGiftconViewHoler {
        return GetGiftconViewHoler(
            ItemCouponBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    class GetGiftconViewHoler(
        private val binding: ItemCouponBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(info: CouponItem) {
            binding.ivCheckbox.visibility = View.VISIBLE
            binding.itemCouponTvTitle.text = info.name
            binding.itemTvExpirationPeriod.visibility = View.GONE

            Glide.with(binding.ivCoupon.context)
                .load(info.imageUrl)
                .placeholder(R.drawable.img_theme1)
                .into(binding.ivCoupon)
        }
    }


    companion object {
        private val GIFTCON_DIFF_CALLBACK = object : DiffUtil.ItemCallback<CouponItem>() {
            override fun areItemsTheSame(oldItem: CouponItem, newItem: CouponItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CouponItem, newItem: CouponItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}