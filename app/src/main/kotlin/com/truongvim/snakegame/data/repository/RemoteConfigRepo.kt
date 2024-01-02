package com.truongvim.snakegame.data.repository

import com.truongvim.snakegame.data.model.RemoteConfigs


/**
 * @Author Mbuodile Obiosio
 * Twitter: @cazewonder
 * Remote Configuration Repository interface
 */
interface RemoteConfigRepo {

    fun initConfigs()

    fun getConfigs(): RemoteConfigs
}