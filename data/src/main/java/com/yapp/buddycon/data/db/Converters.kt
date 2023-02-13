package com.yapp.buddycon.data.db

import androidx.room.TypeConverter
import com.yapp.buddycon.domain.model.CouponType

class Converters {
    @TypeConverter
    fun fromInt(value: Int): CouponType {
        return CouponType.values()[value]
    }

    @TypeConverter
    fun fromCouponType(couponType: CouponType): Int {
        return couponType.ordinal
    }
}