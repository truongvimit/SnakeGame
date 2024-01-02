package com.truongvim.snakegame.data.repository

import com.truongvim.snakegame.data.model.IPLocation
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface IPLocationApi {
    @GET("json")
    suspend fun getDetailIPLocation(): IPLocation
}