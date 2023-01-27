package com.yapp.buddycon.data.network.response

import com.yapp.buddycon.data.mapper.toModel
import com.yapp.buddycon.domain.model.UserInfo


data class UserInfoResponse(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiresIn: Long,
    val grantType: String
){
    fun toModel() = UserInfo(
        accessToken = this.accessToken,
        accessTokenExpiresIn = this.accessTokenExpiresIn,
        refreshToken = this.refreshToken
    )
}