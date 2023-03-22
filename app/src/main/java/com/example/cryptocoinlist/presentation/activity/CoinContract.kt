package com.example.cryptocoinlist.presentation.activity

import com.example.cryptocoinlist.domain.models.Coin

data class CoinState(
    val coinList: List<Coin> = emptyList(),
    val uiState: UiState = UiState.Loading
)

sealed class CoinSideEffect {
    object Loading : CoinSideEffect()
    object ShowCoins: CoinSideEffect()
}

enum class UiState{
    Loading,
    ShowCoins,
    Error
}