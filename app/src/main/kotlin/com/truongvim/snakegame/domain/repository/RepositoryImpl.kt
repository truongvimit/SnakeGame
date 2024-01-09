package com.truongvim.snakegame.domain.repository

import com.truongvim.snakegame.data.model.IPLocation
import com.truongvim.snakegame.data.model.RemoteConfigs
import com.truongvim.snakegame.data.repository.IPLocationApi
import com.truongvim.snakegame.data.repository.IRepository
import com.truongvim.snakegame.data.repository.RemoteConfigRepo

class RepositoryImpl(
    private val remoteConfigRepo: RemoteConfigRepo,
    private val ipLocationApi: IPLocationApi,
): IRepository {
    override suspend fun getDetailIPLocation(): IPLocation = ipLocationApi.getDetailIPLocation()
    override fun initConfigs() = remoteConfigRepo.initConfigs()

    override fun getConfigs() = remoteConfigRepo.getConfigs()
}