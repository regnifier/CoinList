package com.example.cryptocoinlist.data.network

import com.example.cryptocoinlist.BuildConfig

import com.example.cryptocoinlist.domain.models.Coin
import retrofit2.http.GET

interface CoinApi {

    @GET(BuildConfig.GET_CRYPTO_MARKETS)
    suspend fun getCoins() : List<Coin>

}