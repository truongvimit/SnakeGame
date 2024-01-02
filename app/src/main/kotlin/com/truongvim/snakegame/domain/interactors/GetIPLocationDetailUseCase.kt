package com.truongvim.snakegame.domain.interactors

import com.truongvim.snakegame.data.model.IPLocation
import com.truongvim.snakegame.data.repository.IRepository
import domain.interactors.type.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

class GetIPLocationDetailUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Unit, IPLocation>(dispatcher) {
    override suspend fun block(param: Unit): IPLocation = repository.getDetailIPLocation()
}