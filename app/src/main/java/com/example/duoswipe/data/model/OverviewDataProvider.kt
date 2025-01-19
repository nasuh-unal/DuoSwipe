package com.example.duoswipe.data.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

enum class FabState {
    BLANK, ADD, TURN, NEXT, SAVE, FINISH
}

object OverviewDataProvider {
    var fabState by mutableStateOf(FabState.BLANK)
}