package com.example.cryptocoinlist.domain.models

data class Roi(
    val currency: String,
    val percentage: Double,
    val times: Double
)