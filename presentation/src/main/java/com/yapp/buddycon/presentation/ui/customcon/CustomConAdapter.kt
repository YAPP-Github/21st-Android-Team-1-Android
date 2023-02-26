package com.yapp.buddycon.presentation.ui.customcon

import android.annotation.SuppressLint
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yapp.buddycon.domain.model.CouponItem
import com.yapp.buddycon.domain.model.CouponType
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.databinding.ItemCouponBinding
import com.yapp.buddycon.presentation.utils.getDday
import timber.log.Timber
import java.time.LocalDate
import java.time.Period
import java.util.*

class CustomConAdapter(
    private val onClickListener: ((CouponItem) -> Unit)? = null
) : PagingDataAdapter<CouponItem, CustomConAdapter.CustommConViewHoler>(CUSTOMCON_DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: CustommConViewHoler, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustommConViewHoler {
        return CustommConViewHoler(
            ItemCouponBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class CustommConViewHoler(
        private val binding: ItemCouponBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(info: CouponItem) {
            binding.root.setOnClickListener { onClickListener?.invoke(info) }
            binding.itemCouponTvTitle.text = info.name
            binding.itemTvExpirationPeriod.text = "~${info.expireDate.replace("-", ".")}"
            if (info.usable) {
                val (year, month, day) = info.expireDate.split("-").map { it.toInt() }
                val diff = Calendar.getInstance().getDday(year, month, day)
                if (diff in 0..14) {
                    binding.ivAlert.isVisible = false
                    binding.btnExpireDate.isVisible = true
                    binding.btnExpireDate.text = "D-${diff}"
                    binding.btnExpireDate.setBackgroundResource(
                        if (diff <= 7) R.drawable.bg_coupon_expire_date
                        else R.drawable.bg_coupon_gray_expire_date
                    )
                } else if (diff < 0) {
                    binding.ivAlert.isVisible = true
                    binding.btnExpireDate.isVisible = false
                } else {
                    binding.ivAlert.isVisible = false
                    binding.btnExpireDate.isVisible = false
                }

                binding.ivCoupon.colorFilter = null
            } else {
                binding.btnExpireDate.isVisible = false
                binding.ivAlert.isVisible = false
                if (info.couponType == CouponType.Made) {
                    binding.ivCoupon.colorFilter = null
                } else {
                    binding.ivCoupon.colorFilter =
                        ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0F) })
                }
            }

            binding.tvNoshared.isVisible = info.shared.not()

            Glide.with(binding.ivCoupon.context)
                .load(info.imageUrl)
                .placeholder(R.drawable.img_theme1)
                .into(binding.ivCoupon)
        }
    }


    companion object {
        private val CUSTOMCON_DIFF_CALLBACK = object : DiffUtil.ItemCallback<CouponItem>() {
            override fun areItemsTheSame(oldItem: CouponItem, newItem: CouponItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CouponItem, newItem: CouponItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}