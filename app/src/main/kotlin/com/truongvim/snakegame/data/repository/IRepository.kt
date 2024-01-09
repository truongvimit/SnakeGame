package com.truongvim.snakegame.data.repository

import com.truongvim.snakegame.data.model.IPLocation
import com.truongvim.snakegame.data.model.RemoteConfigs
import kotlinx.coroutines.flow.Flow

interface IRepository {
    suspend fun getDetailIPLocation(): IPLocation
    fun initConfigs()

    fun getConfigs(): Flow<RemoteConfigs>
}