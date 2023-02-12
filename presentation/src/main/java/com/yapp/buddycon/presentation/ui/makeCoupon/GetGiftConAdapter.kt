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
import com.yapp.buddycon.presentation.databinding.ItemMakeCouponBinding

class GetGiftConAdapter(private val itemClickListener : (CouponItem) -> (Unit)) :
    PagingDataAdapter<CouponItem, GetGiftConAdapter.GetGiftconViewHoler>(GIFTCON_DIFF_CALLBACK) {
    var selectedItem: CouponItem? = null

    override fun onBindViewHolder(holder: GetGiftconViewHoler, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetGiftconViewHoler {
        return GetGiftconViewHoler(
            ItemMakeCouponBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class GetGiftconViewHoler(
        private val binding: ItemMakeCouponBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(info: CouponItem) {
            binding.ivCheckbox.visibility = if (info == selectedItem) { View.VISIBLE } else { View.GONE }
            binding.ivCoupon.foreground =if (info == selectedItem) {binding.ivCoupon.context.getDrawable(R.color.skyblue_a20)} else null

            binding.itemCouponTvTitle.text = info.name
            Glide.with(binding.ivCoupon.context)
                .load(info.imageUrl)
                .placeholder(R.drawable.img_theme1)
                .into(binding.ivCoupon)

            itemView.setOnClickListener {
                selectedItem = info
                notifyDataSetChanged()
                itemClickListener(info)
            }
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