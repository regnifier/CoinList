package com.example.cryptocoinlist.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.cryptocoinlist.presentation.activity.CoinSideEffect
import com.example.cryptocoinlist.presentation.activity.MainViewModel
import com.example.cryptocoinlist.presentation.activity.UiState


@Composable
fun MainScreen(viewModel: MainViewModel) {
    val state by viewModel.container.stateFlow.collectAsState()
    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collect {
            when (it) {
                CoinSideEffect.Loading -> {
                    Log.d("test", "Loading")
                }
                CoinSideEffect.ShowCoins -> {
                    Log.d("test", "ShowCoins")
                }
            }
        }
    }

    when (state.uiState) {
        UiState.Loading -> {
            LoadingScreen(modifier = Modifier.fillMaxSize())
        }
        UiState.ShowCoins -> {
            CoinListScreen(
                state = state,
                onCurrentTextChange = { text -> viewModel.search(text) },
                modifier = Modifier.fillMaxSize()
            )
        }
        UiState.Error -> {
            ErrorScreen(
                onRetryClick = { viewModel.loadCoinList() },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

