package com.truongvim.snakegame.domain.interactors

import com.truongvim.snakegame.data.model.IPLocation
import com.truongvim.snakegame.data.model.RemoteConfigs
import com.truongvim.snakegame.data.repository.IRepository
import domain.interactors.type.BaseUseCase
import domain.interactors.type.BaseUseCaseFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetRemoteConfigUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCaseFlow<Unit, RemoteConfigs>(dispatcher) {
    override suspend fun build(param: Unit): Flow<RemoteConfigs> = repository.getConfigs()
}