package com.truongvim.snakegame.data.repository

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.truongvim.snakegame.BuildConfig
import com.truongvim.snakegame.data.model.RemoteConfigs
import com.truongvim.snakegame.utils.DefaultConfigs
import com.truongvim.snakegame.utils.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

/**
 * @Author Mbuodile Obiosio
 * Twitter: @cazewonder
 */
class RemoteConfigRepoImpl : RemoteConfigRepo {

    // Get remote config instance
    private val remoteConfig = Firebase.remoteConfig

    private val _configs = MutableStateFlow(RemoteConfigs())
    private val configs = _configs.asStateFlow()

    init {
        initConfigs()
    }

    override fun initConfigs() {
        /**
         * [cacheInterval] defines the interval of fetches per hour.
         * Use [remoteConfigSettings] to set the minimum fetch interval
         * */
        val cacheInterval = 3000L // 3000 milliseconds Long equivalent of 3 seconds
        val minFetchInterval: Long = if (BuildConfig.DEBUG) {
            0
        } else {
            cacheInterval
        }
        val configSettings = remoteConfigSettings {
            fetchTimeoutInSeconds = 20L
            minimumFetchIntervalInSeconds = minFetchInterval
        }
        // [END config settings]

        /*
        * Set the default parameters for Remote Config
        * Your app will use these default values until there's a change in the firebase console
        * */
        remoteConfig.apply {
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(DefaultConfigs.getDefaultParams())
        }
        // [END default config]

        /**
         * Fetch updates from Firebase console
         * */
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { taskResult ->
                if (taskResult.isSuccessful) {
                    Log.d(TAG, "Successful ${taskResult.result}")
                    _configs.update {
                        it.copy(
                            urlPoint = remoteConfig.getString("url_point")
                        )
                    }
                } else {
                    Log.d(TAG, "Failed ${taskResult.result}")
                }
            }.addOnFailureListener {
                Log.d(TAG, "Exception ${it.message}")
            }
        // [End fetch and activate]
    }

    /**
     * @return [RemoteConfigs] remote values
     * */
    override fun getConfigs() = flow {
        configs.collect {
            emit(it)
        }
    }
}