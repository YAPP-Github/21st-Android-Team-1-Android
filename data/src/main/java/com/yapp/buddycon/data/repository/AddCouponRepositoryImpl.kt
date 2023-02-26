package com.yapp.buddycon.data.repository

import com.google.gson.Gson
import com.yapp.buddycon.data.datasource.remote.addcoupon.AddCouponRemoteDataSource
import com.yapp.buddycon.data.mapper.mapCouponInputInfoToCustomCouponAddRequest
import com.yapp.buddycon.data.mapper.mapCouponInputInfoToGifticonAddRequest
import com.yapp.buddycon.data.network.response.CustomCouponInfoResponse
import com.yapp.buddycon.data.network.response.GetCouponInfoErrorResponse
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
                if (response.isSuccessful) {
                    Timber.tag("AppTest").e("response success")
                    (response.body()
                        ?: throw NullPointerException("null response")).mapToCouponInputInfo()
                } else {
                    if (response.code() == 400) {
                        val gson = Gson()
                        val errorResponse = gson.fromJson(
                            response.errorBody()?.charStream(),
                            GetCouponInfoErrorResponse::class.java
                        )
                        Timber.tag("AppTest").e("error parse : ${errorResponse.code}")

                        if (errorResponse.code == "NOT_EXIST_BARCODE_NUMBER") {
                            // 최초 등록하는 기프티콘
                            Timber.tag("AppTest").e("NOT_EXIST_BARCODE_NUMBER")
                            GifticonInfoResponse().mapToCouponInputInfo()
                        } else {
                            // 누군가 먼저 등록한 기프티콘
                            // 추후 state 따로 분리하기
                            GifticonInfoResponse().mapToCouponInputInfo()
                        }
                    } else {
                        throw Throwable("error.. msg : ${response.message()}")
                    }
                }

//                (response.body() ?: if (response.code() == 204) {
//                    GifticonInfoResponse() // 서버에 존재하지 않는 바코드 넘버
//                } else if(response.code() == 400) {
//                    Timber.tag("AppTest").e("response code 400, state message : ${response.body()?.couponStateMessage}")
//                    throw Throwable("error code 400")
//                } else {
//                    throw NullPointerException("null response")
//                }).mapToCouponInputInfo()
            }
    }

    override fun getCustomCouponInfo(barcodeNumber: String): Flow<CouponInputInfo> {
        return addCouponRemoteDataSource.getCustomCouponInfo(barcodeNumber)
            .catch { error ->
                Timber.tag("AppTest").e("catch in repositoryImpl")
                throw Throwable("catch error!", error)
            }.map { response ->
                if (response.isSuccessful) {
                    Timber.tag("AppTest").e("response success")
                    (response.body()
                        ?: throw NullPointerException("null response")).mapToCouponInputInfo()
                } else {
                    if (response.code() == 400) {
                        val gson = Gson()
                        val errorResponse = gson.fromJson(
                            response.errorBody()?.charStream(),
                            GetCouponInfoErrorResponse::class.java
                        )
                        Timber.tag("AppTest").e("error parse : ${errorResponse.code}")

                        if (errorResponse.code == "NOT_EXIST_BARCODE_NUMBER") {
                            // 최초 등록하는 기프티콘
                            Timber.tag("AppTest").e("NOT_EXIST_BARCODE_NUMBER")
                            GifticonInfoResponse().mapToCouponInputInfo()
                        } else {
                            // 누군가 먼저 등록한 제작티콘
                            GifticonInfoResponse().mapToCouponInputInfo()
                        }
                    } else {
                        throw Throwable("error.. msg : ${response.message()}")
                    }
                }



//                    response ->
//                (response.body() ?: if (response.code() == 204) {
//                    CustomCouponInfoResponse() // 서버에 존재하지 않는 바코드 넘버
//                } else {
//                    throw NullPointerException("null response")
//                }).mapToCouponInputInfo()
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