package com.truongvim.snakegame.data.model

data class State(
    val food: Pair<Int, Int>,
    val snake: List<Pair<Int, Int>>,
    val currentDirection: Int
)