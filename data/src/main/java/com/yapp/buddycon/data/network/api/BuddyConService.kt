package com.yapp.buddycon.data.network.api

import com.yapp.buddycon.data.network.response.ExampleResponse
import retrofit2.http.GET
import retrofit2.http.Path

// TODO Example 추후 삭제 예정
interface BuddyConService {

    @GET("api/v1/hello")
    suspend fun getString(): ExampleResponse
}