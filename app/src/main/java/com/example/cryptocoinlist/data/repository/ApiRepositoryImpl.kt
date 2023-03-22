package com.example.cryptocoinlist.data.repository

import com.example.cryptocoinlist.data.network.CoinApi
import com.example.cryptocoinlist.domain.ApiRepository
import com.example.cryptocoinlist.domain.models.Coin

class ApiRepositoryImpl(private val coinApi: CoinApi): ApiRepository {

    override suspend fun getCoins(): List<Coin> = coinApi.getCoins()

}