package com.yapp.buddycon.data.datasource.remote.addcoupon

import android.net.Uri
import android.util.Log
import com.google.gson.Gson
import com.yapp.buddycon.data.network.api.AddCouponService
import com.yapp.buddycon.data.network.request.GifticonAddRequest
import com.yapp.buddycon.data.network.response.AddCouponResponse
import com.yapp.buddycon.data.network.response.CustomCouponInfoResponse
import com.yapp.buddycon.data.network.response.GifticonInfoResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class AddCouponRemoteDataSourceImpl @Inject constructor(
    private val addCouponService: AddCouponService
)  : AddCouponRemoteDataSource {
    override fun getGifticonInfo(barcodeNumber: String): Flow<Response<GifticonInfoResponse>> {
        return flow {
            emit(addCouponService.getGifticonInfo(barcodeNumber))
        }
    }

    override fun getCustomCouponInfo(barcodeNumber: String): Flow<Response<CustomCouponInfoResponse>> {
        return flow {
            emit(addCouponService.getCustomCouponInfo(barcodeNumber))
        }
    }

    override fun addGifticon(
        imageUriPath: String,
        addGifticonAddRequest: GifticonAddRequest
    ): Flow<Response<AddCouponResponse>> {
        return flow {
            val imageFile = File(imageUriPath)
            //val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
            val imageReuestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
            val imageMultipartBody = MultipartBody.Part.createFormData("image", imageFile.name, imageReuestBody)

            Log.e("AppTest", "request data : ${addGifticonAddRequest}")
            val json = Gson().toJson(addGifticonAddRequest)
            Log.e("AppTest", "json : ${json}")
            val infoRequestBody = json.toRequestBody("application/json".toMediaTypeOrNull())
            val infoMultipartBody = MultipartBody.Part.createFormData("gifticonCreationRequestDto", null, infoRequestBody)

            emit(addCouponService.addGifticon(imageMultipartBody, infoMultipartBody))
        }
    }

}