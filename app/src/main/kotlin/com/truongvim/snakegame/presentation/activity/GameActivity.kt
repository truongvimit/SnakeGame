package com.truongvim.snakegame.presentation.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.lifecycleScope
import com.truongvim.snakegame.R
import com.truongvim.snakegame.data.cache.GameCache
import com.truongvim.snakegame.data.model.HighScore
import com.truongvim.snakegame.domain.base.BaseActivity
import com.truongvim.snakegame.domain.base.TOP_10
import com.truongvim.snakegame.domain.game.GameEngine
import com.truongvim.snakegame.presentation.screen.EndScreen
import com.truongvim.snakegame.presentation.screen.GameScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GameActivity : BaseActivity() {
    private lateinit var dataStore: GameCache
    private val isPlaying = mutableStateOf(true)
    private var score = mutableStateOf(0)
    private lateinit var scope: CoroutineScope
    private lateinit var playerName: String
    private lateinit var highScores: List<HighScore>
    private var gameEngine = GameEngine(
        scope = lifecycleScope,
        onGameEnded = {
            if (isPlaying.value) {
                isPlaying.value = false
                scope.launch { dataStore.saveHighScore(highScores) }
            }
        },
        onFoodEaten = { score.value++ }
    )

    @Composable
    override fun Content() {
        scope = rememberCoroutineScope()
        dataStore = GameCache(applicationContext)
        playerName =
            dataStore.getPlayerName.collectAsState(initial = stringResource(id = R.string.default_player_name)).value
        highScores = dataStore.getHighScores.collectAsState(initial = listOf()).value.plus(
            HighScore(playerName, score.value)
        ).sortedByDescending { it.score }.take(TOP_10)
        Column {
            if (isPlaying.value) {
                GameScreen(gameEngine, score.value)
            } else {
                EndScreen(score.value) {
                    score.value = 0
                    gameEngine.reset()
                    isPlaying.value = true
                }
            }
        }
    }
}