package com.truongvim.snakegame.presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truongvim.snakegame.domain.interactors.GetIPLocationDetailUseCase
import com.truongvim.snakegame.domain.interactors.GetRemoteConfigUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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
            getRemoteConfigUseCase(Unit).collectLatest {
                it.onSuccess { remoteConfigs ->
                    getIPLocationDetailUseCase(Unit)
                        .onSuccess { ipLocation ->
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
}

data class MainStateUi(
    val urlPoint: String? = null,
    val countryCode: String? = null,
)