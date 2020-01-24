package com.vladimirkomlev.currencynotifier.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CurrencyRate(
        val disclaimer: String,
        val license: String,
        val timestamp: Long,
        val base: String,
        val rates: Rate
)

data class Rate(
        @JsonProperty("KZT") val kzt: Double,
        @JsonProperty("RUB") val rub: Double
)