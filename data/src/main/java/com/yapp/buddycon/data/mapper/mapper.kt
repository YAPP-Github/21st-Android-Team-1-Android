package com.yapp.buddycon.data.mapper

import com.yapp.buddycon.data.network.response.UserInfoResponse
import com.yapp.buddycon.domain.model.UserInfo

fun UserInfoResponse.toModel() = UserInfo(
    accessToken = this.accessToken,
    accessTokenExpiresIn = this.accessTokenExpiresIn
)
