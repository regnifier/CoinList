package com.example.cryptocoinlist.presentation.activity

import androidx.compose.runtime.Immutable
import com.example.cryptocoinlist.domain.models.Coin

@Immutable
data class CoinState(
    val coinList: List<Coin> = emptyList(),
    val searchText: String = "",
    val uiState: UiState = UiState.Loading
)

@Immutable
sealed interface CoinSideEffect {
    object Loading : CoinSideEffect
    object ShowCoins : CoinSideEffect
}

@Immutable
sealed interface UiState {
    object Loading : UiState
    object ShowCoins : UiState
    object Error : UiState
}