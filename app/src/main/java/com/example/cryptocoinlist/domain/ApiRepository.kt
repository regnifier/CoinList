package com.example.cryptocoinlist.domain

import com.example.cryptocoinlist.domain.models.Coin

interface ApiRepository {

    suspend fun getCoins(): List<Coin>

}