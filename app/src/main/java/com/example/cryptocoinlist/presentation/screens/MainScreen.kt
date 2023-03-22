package com.example.cryptocoinlist.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.cryptocoinlist.presentation.activity.MainViewModel
import com.example.cryptocoinlist.presentation.activity.UiState
import kotlinx.collections.immutable.toImmutableList
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState


@Composable
fun MainScreen(viewModel: MainViewModel = koinViewModel()) {
    val state by viewModel.collectAsState()

    when (state.uiState) {
        UiState.Loading -> {
            LoadingScreen(modifier = Modifier.fillMaxSize())
        }
        UiState.ShowCoins -> {
            CoinListScreen(
                coinList = state.coinList.toImmutableList(),
                onCurrentTextChange = viewModel::search,
                modifier = Modifier.fillMaxSize(),
                searchText = state.searchText
            )
        }
        UiState.Error -> {
            ErrorScreen(
                onRetryClick = viewModel::loadCoinList,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

