package com.truongvim.snakegame.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.truongvim.snakegame.data.model.State
import com.truongvim.snakegame.domain.game.GameEngine
import com.truongvim.snakegame.presentation.theme.DarkGreen
import com.truongvim.snakegame.presentation.theme.border2dp
import com.truongvim.snakegame.presentation.theme.corner4dp
import com.truongvim.snakegame.presentation.theme.padding16dp

@Composable
fun Board(state: State) {
    BoxWithConstraints(Modifier.padding(padding16dp)) {
        val tileSize = maxWidth / GameEngine.BOARD_SIZE
        Box(
            Modifier
                .size(maxWidth)
                .border(border2dp, DarkGreen)
        )
        Box(
            Modifier
                .offset(x = tileSize * state.food.first, y = tileSize * state.food.second)
                .size(tileSize)
                .background(
                    DarkGreen, CircleShape
                )
        )
        state.snake.forEach {
            Box(
                modifier = Modifier
                    .offset(x = tileSize * it.first, y = tileSize * it.second)
                    .size(tileSize)
                    .background(
                        DarkGreen, RoundedCornerShape(corner4dp)
                    )
            )
        }
    }
}