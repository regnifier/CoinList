package com.example.cryptocoinlist.presentation.activity

import androidx.compose.runtime.Immutable
import com.example.cryptocoinlist.domain.models.Coin
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class CoinState(
    val coinList: ImmutableList<Coin> = persistentListOf(),
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