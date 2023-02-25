package com.yapp.buddycon.data.repository

import com.yapp.buddycon.data.datasource.remote.addcoupon.AddCouponRemoteDataSource
import com.yapp.buddycon.data.mapper.mapCouponInputInfoToCustomCouponAddRequest
import com.yapp.buddycon.data.mapper.mapCouponInputInfoToGifticonAddRequest
import com.yapp.buddycon.data.network.response.CustomCouponInfoResponse
import com.yapp.buddycon.data.network.response.GifticonInfoResponse
import com.yapp.buddycon.domain.model.AddCouponResult
import com.yapp.buddycon.domain.model.CouponInputInfo
import com.yapp.buddycon.domain.repository.AddCouponRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class AddCouponRepositoryImpl @Inject constructor(
    private val addCouponRemoteDataSource: AddCouponRemoteDataSource
) : AddCouponRepository {
    override fun getGifticionInfo(barcodeNumber: String): Flow<CouponInputInfo> {
        return addCouponRemoteDataSource.getGifticonInfo(barcodeNumber)
            .catch { error ->
                throw Throwable("catch error!", error)
            }.map { response ->
                (response.body() ?: if (response.code() == 204) {
                    GifticonInfoResponse() // 서버에 존재하지 않는 바코드 넘버
                } else {
                    throw NullPointerException("null response")
                }).mapToCouponInputInfo()
            }
    }

    override fun getCustomCouponInfo(barcodeNumber: String): Flow<CouponInputInfo> {
        return addCouponRemoteDataSource.getCustomCouponInfo(barcodeNumber)
            .catch { error ->
                Timber.tag("AppTest").e("catch in repositoryImpl")
                throw Throwable("catch error!", error)
            }.map { response ->
                (response.body() ?: if (response.code() == 204) {
                    CustomCouponInfoResponse() // 서버에 존재하지 않는 바코드 넘버
                } else {
                    throw NullPointerException("null response")
                }).mapToCouponInputInfo()
            }
    }

    override fun addGifticon(
        imageUriPath: String,
        couponInputInfo: CouponInputInfo
    ): Flow<AddCouponResult> {
        return addCouponRemoteDataSource.addGifticon(
            imageUriPath,
            mapCouponInputInfoToGifticonAddRequest(couponInputInfo)
        )
            .catch { error ->
                Timber.tag("AppTest").e("addGifticon / catch in repositoryImpl / error : ${error}")
                throw Throwable("catch error!", error)
            }.map { response ->
                (response.body() ?: throw NullPointerException("null response"))
                    .mapToAddCouponResult()
            }
    }

    override fun addCustomCoupon(
        imageUriPath: String,
        couponInputInfo: CouponInputInfo
    ): Flow<AddCouponResult> {
        return addCouponRemoteDataSource.addCustomCoupon(
            imageUriPath,
            mapCouponInputInfoToCustomCouponAddRequest(couponInputInfo)
        )
            .catch { error ->
                Timber.tag("AppTest")
                    .e("add Custom coupon / catch in repositoryImpl / error : ${error}")
                throw Throwable("catch error!", error)
            }.map { response ->
                (response.body() ?: throw NullPointerException("null response"))
                    .mapToAddCouponResult()
            }
    }
}