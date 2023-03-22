package com.example.cryptocoinlist.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cryptocoinlist.presentation.screens.MainScreen
import com.example.cryptocoinlist.ui.theme.CryptoCoinListTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CryptoCoinListTheme {
                MainScreen(mainViewModel)
            }
        }
    }
}



