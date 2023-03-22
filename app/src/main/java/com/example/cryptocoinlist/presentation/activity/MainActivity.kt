package com.example.cryptocoinlist.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cryptocoinlist.presentation.screens.MainScreen
import com.example.cryptocoinlist.ui.theme.CryptoCoinListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoCoinListTheme {
                MainScreen()
            }
        }
    }
}



