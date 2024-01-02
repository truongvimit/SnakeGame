package com.truongvim.snakegame.presentation.activity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truongvim.snakegame.domain.interactors.GetIPLocationDetailUseCase
import com.truongvim.snakegame.domain.interactors.GetRemoteConfigUseCase
import com.truongvim.snakegame.utils.TAG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    val getIPLocationDetailUseCase: GetIPLocationDetailUseCase,
    val getRemoteConfigUseCase: GetRemoteConfigUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainStateUi())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getIPLocationDetailUseCase(Unit)
                .onSuccess { ipLocation ->
                    getRemoteConfigUseCase(Unit).onSuccess { remoteConfigs ->
                        _uiState.update { mainStateUi ->
                            mainStateUi.copy(
                                countryCode = ipLocation.countryCode,
                                urlPoint = remoteConfigs.urlPoint
                            )
                        }
                    }
                }
        }
    }
}

data class MainStateUi(
    val urlPoint: String? = null,
    val countryCode: String? = null,
)