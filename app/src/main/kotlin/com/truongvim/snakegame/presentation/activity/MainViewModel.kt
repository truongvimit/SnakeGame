package com.truongvim.snakegame.presentation.activity

import androidx.lifecycle.ViewModel
import com.truongvim.snakegame.data.repository.RemoteConfigRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(
    remoteConfigRepo: RemoteConfigRepo
) : ViewModel(){
    private val _uiState = MutableStateFlow(MainStateUi())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                urlPoint = remoteConfigRepo.getConfigs().urlPoint
            )
        }
    }
}

data class MainStateUi(
    val urlPoint : String? = null,
)